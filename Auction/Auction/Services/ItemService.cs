using Auction.Data;
using Auction.DTO;
using Auction.Mappers;
using Auction.Models;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Auction.Services
{
    public class ItemService
    {
        private readonly AuctionContext context;

        public ItemService(AuctionContext context)
        {
            this.context = context;
        }

        public async Task<IEnumerable<ItemDTO>> GetAllItemsAsync()
        {
            var items = await context.Items.Include(i => i.Category).ToListAsync();
            return items.Select(ItemMapper.ToDTO);
        }

        public async Task<ItemDTO> GetItemByIdAsync(int id)
        {
            var item = await context.Items.Include(i => i.Category).FirstOrDefaultAsync(i => i.Id == id);
            if (item == null) return null;
            return ItemMapper.ToDTO(item);
        }

        public async Task<IEnumerable<ItemDTO>> GetAvailableItemsAsync()
        {
            var items = await context.Items.Include(i => i.Category).Where(i => i.IsAvailable).ToListAsync();
            return items.Select(ItemMapper.ToDTO);
        }

        public async Task<IEnumerable<ItemDTO>> GetItemsByPriceRangeAsync(float minPrice, float maxPrice)
        {
            var items = await context.Items.Include(i => i.Category)
                .Where(i => i.Price >= minPrice && i.Price <= maxPrice)
                .ToListAsync();
            return items.Select(ItemMapper.ToDTO);
        }

        public async Task<ItemDTO> CreateItemAsync(Item item)
        {
            context.Items.Add(item);
            await context.SaveChangesAsync();
            return ItemMapper.ToDTO(item);
        }

        public async Task<bool> UpdateItemAsync(int id, Item item)
        {
            if (id != item.Id) return false;

            context.Entry(item).State = EntityState.Modified;

            try
            {
                await context.SaveChangesAsync();
                return true;
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!await ItemExistsAsync(id)) return false;
                throw;
            }
        }

        public async Task<bool> DeleteItemAsync(int id)
        {
            var item = await context.Items.FirstOrDefaultAsync(i => i.Id == id);
            if (item == null || !item.IsAvailable) return false;

            context.Items.Remove(item);
            await context.SaveChangesAsync();
            return true;
        }

        private async Task<bool> ItemExistsAsync(int id)
        {
            return await context.Items.AnyAsync(i => i.Id == id);
        }
    }
}

