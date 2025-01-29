using Auction.DTO;
using Auction.Models;

namespace Auction.Mappers
{
    public static class CategoryMapper
    {
        public static CategoryWithItemDTO ToDTOWithItem(Category category)
        {
            List<ItemDTO> itemDTOs = new List<ItemDTO>();
            foreach (Item item in category.Items)
            {
                itemDTOs.Add(ItemMapper.ToDto(item));
            }
            return new CategoryWithItemDTO
            {
                Id = category.Id,
                Description = category.Description, 
                Items = itemDTOs

            };
        }

        public static CategoryDTO ToDTO(Category category)
        {
            return new CategoryDTO
            {
                Id = category.Id,
                Description = category.Description
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
