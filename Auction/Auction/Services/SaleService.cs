using Auction.Data;
using Auction.DTO;
using Auction.Mappers;
using Auction.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Auction.Services
{
    public class SaleService
    {
        private readonly AuctionContext context;

        public SaleService(AuctionContext context)
        {
            this.context = context;
        }

        public async Task<List<SaleDTO>> GetAllSaleAsync()
        {
            var sales = await context.Sale
                .Include(s => s.Item)
                .ToListAsync();

            return sales.Select(SaleMapper.ToDto).ToList();
        }

        public async Task<SaleDTO> GetSaleByIdAsync(int saleId)
        {
            var sale = await context.Sale
                .Include(s => s.Item)
                .FirstOrDefaultAsync(s => s.Id == saleId);

            if (sale == null) return null;
            return SaleMapper.ToDto(sale);
        }

        public async Task<List<SaleDTO>> GetSaleByPriceRangeAsync(float minPrice, float maxPrice)
        {
            var sales = await context.Sale
                .Include(s => s.Item)
                .Where(s => s.SalePrice >= minPrice && s.SalePrice <= maxPrice)
                .ToListAsync();

            return sales.Select(SaleMapper.ToDto).ToList();
        }

        public async Task<List<SaleDTO>> GetSaleByItemIdAsync(int itemId)
        {
            var sales = await context.Sale
                .Include(s => s.Item)
                .Where(s => s.ItemId == itemId)
                .ToListAsync();

            return sales.Select(SaleMapper.ToDto).ToList();
        }

        public async Task<List<SaleDTO>> GetSaleByDateAsync(DateTime date)
        {
            var sales = await context.Sale
                .Include(s => s.Item)
                .Where(s => s.SaleDate.Date == date.Date)
                .ToListAsync();

            return sales.Select(SaleMapper.ToDto).ToList();
        }

        public async Task<List<SaleDTO>> GetSaleBetweenDatesAsync(DateTime startDate, DateTime endDate)
        {
            var sales = await context.Sale
                .Include(s => s.Item)
                .Where(s => s.SaleDate.Date >= startDate.Date && s.SaleDate.Date <= endDate.Date)
                .ToListAsync();

            return sales.Select(SaleMapper.ToDto).ToList();
        }

        public async Task<SaleDTO> CreateSaleAsync(SaleDTOCreate saleDto)
        {
            var sale = SaleMapper.ToModelCreate(saleDto);

            var item = await context.Items.FirstOrDefaultAsync(i => i.Id == sale.ItemId);
            if (item == null)
            {
                throw new Exception("Item does not exist.");
            }

            if (!item.IsAvailable)
            {
                throw new Exception("Item is already sold (not available).");
            }

            item.IsAvailable = false;

            context.Sale.Add(sale);
            await context.SaveChangesAsync();

            await context.Entry(sale).Reference(s => s.Item).LoadAsync();

            return SaleMapper.ToDto(sale);
        }

      

        public async Task<bool> DeleteSaleAsync(int saleId)
        {
            var sale = await context.Sale.FirstOrDefaultAsync(s => s.Id == saleId);
            if (sale == null) return false;

            var item = await context.Items.FirstOrDefaultAsync(i => i.Id == sale.ItemId);
            if (item != null)
            {
                item.IsAvailable = true;
            }

            context.Sale.Remove(sale);
            await context.SaveChangesAsync();

            return true;
        }
    }
}
