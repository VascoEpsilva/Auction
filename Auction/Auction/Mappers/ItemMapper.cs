using Auction.DTO;
using Auction.Models;
using Microsoft.EntityFrameworkCore;

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
                CategoryName = item.Category.Description
            };
        }

        public static ItemDTOCreate ToDTOCreate(Item item)
        {
            return new ItemDTOCreate
            {
                Id = item.Id,
                Name = item.Name,
                Description = item.Description,
                Price = item.Price,
                CategoryId = item.CategoryId
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

        public static Item ToModelCreation(ItemDTOCreate itemDtoCreate)
        {
            return new Item
            { 
                Name = itemDtoCreate.Name,
                Description = itemDtoCreate.Description,
                Price = itemDtoCreate.Price,
                IsAvailable = true,
                CategoryId = itemDtoCreate.CategoryId
            };
        }

        public static Item ToModelUpdate(ItemDTOUpdate itemDto)
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
