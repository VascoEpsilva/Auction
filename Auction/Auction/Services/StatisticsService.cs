using Auction.Data;
using Auction.DTO;
using Auction.Mappers;
using Auction.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace Auction.Services
{
    public class StatisticsService
    {
        private readonly AuctionContext context;

        public StatisticsService(AuctionContext context)
        {
            this.context = context;
        }


        public async Task<int> GetTotalItemsAsync()
        {
            return await context.Items
                .CountAsync();
        }


        public async Task<int> GetTotalAvailableItemsAsync()
        {
            return await context.Items
                .Where(i => i.IsAvailable)
                .CountAsync();
        }

        public async Task<int> GetTotalSalesAsync()
        {
            return await context.Sale.CountAsync();
        }

        public async Task<int> GetItemsCountByCategoryIdAsync(int categoryId)
        {
            return await context.Items
                .Where(i => i.CategoryId == categoryId)
                .CountAsync();
        }

        public async Task<float> GetAveragePriceAsync()
        {
            if (!await context.Items.AnyAsync()) return 0;
            return await context.Items.AverageAsync(i => i.Price);
        }

        public async Task<float> GetAveragePriceByCategoryIdAsync(int categoryId)
        {
            var itemsQuery = context.Items.Where(i => i.CategoryId == categoryId);
            if (!await itemsQuery.AnyAsync()) return 0;
            return await itemsQuery.AverageAsync(i => i.Price);
        }

        public async Task<SaleDTO> GetCheapestItemSoldAsync()
        {
            var cheapestSale = await context.Sale
                .Include(s => s.Item)
                .OrderBy(s => s.SalePrice)
                .FirstOrDefaultAsync();

            return cheapestSale == null ? null : SaleMapper.ToDto(cheapestSale);
        }

        public async Task<SaleDTO> GetCheapestSoldByCategoryAsync(int categoryId)
        {
            var cheapestSale = await context.Sale
                .Include(s => s.Item)
                .Where(s => s.Item.CategoryId == categoryId)
                .OrderBy(s => s.SalePrice)
                .FirstOrDefaultAsync();

            return cheapestSale == null ? null : SaleMapper.ToDto(cheapestSale);
        }

        public async Task<SaleDTO> GetCheapestSoldByMonthAsync(int year, int month)
        {
            var cheapestSale = await context.Sale
                .Include(s => s.Item)
                .Where(s => s.SaleDate.Year == year && s.SaleDate.Month == month)
                .OrderBy(s => s.SalePrice)
                .FirstOrDefaultAsync();

            return cheapestSale == null ? null : SaleMapper.ToDto(cheapestSale);
        }

        public async Task<ItemDTO> GetMostExpensiveItemSoldAsync()
        {
            var sale = await context.Sale
                .Include(s => s.Item)
                .OrderByDescending(s => s.SalePrice)
                .FirstOrDefaultAsync();

            if (sale == null) return null;
            return ConvertSaleToItemDto(sale);
        }
        public async Task<ItemDTO> GetMostExpensiveItemSoldByCategoryIdAsync(int categoryId)
        {
            var sale = await context.Sale
                .Include(s => s.Item)
                .Where(s => s.Item.CategoryId == categoryId)
                .OrderByDescending(s => s.SalePrice)
                .FirstOrDefaultAsync();

            if (sale == null) return null;
            return ConvertSaleToItemDto(sale);
        }

        public async Task<ItemDTO> GetMostExpensiveItemSoldByMonthAsync(int year, int month)
        {
            var sale = await context.Sale
                .Include(s => s.Item)
                .Where(s => s.SaleDate.Year == year && s.SaleDate.Month == month)
                .OrderByDescending(s => s.SalePrice)
                .FirstOrDefaultAsync();

            if (sale == null) return null;
            return ConvertSaleToItemDto(sale);
        }

        private ItemDTO ConvertSaleToItemDto(Sale sale)
        {
            var item = sale.Item;
            if (item == null) return null;

            return new ItemDTO
            {
                Id = item.Id,
                Name = item.Name,
                Description = item.Description,
                Price = item.Price,
                IsAvailable = item.IsAvailable,
                CategoryId = item.CategoryId,
                CategoryName = item.Category?.Description
            };
        }
    }
}
