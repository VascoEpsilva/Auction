using Auction.DTO;
using Auction.Models;

namespace Auction.Mappers
{
    public static class ItemMapper
    {
        public static ItemDTO ToDTO(Item item)
        {
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

        public static Item ToModel(ItemDTO itemDto)
        {
            return new Item
            {
                Id = itemDto.Id,
                Name = itemDto.Name,
                Description = itemDto.Description,
                Price = itemDto.Price,
                IsAvailable = itemDto.IsAvailable,
                CategoryId = itemDto.CategoryId
            };
        }
    }
}
