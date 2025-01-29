using Auction.DTO;
using Auction.Models;

namespace Auction.Mappers
{
    public static class CategoryMapper
    {
        public static CategoryDTO ToDto(Category category)
        {
            List<ItemDTO> itemDTOs = new List<ItemDTO>();
            foreach (Item item in category.Items)
            {
                itemDTOs.Add(ItemMapper.ToDto(item));
            }
            return new CategoryDTO
            {
                Id = category.Id,
                Description = category.Description, 
                Items = itemDTOs

            };
        }

        public static Category ToModel(CategoryDTO categoryDto)
        {
            return new Category
            {
                Id = categoryDto.Id,
                Description = categoryDto.Description};
        }
    }
}
