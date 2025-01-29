namespace Auction.DTO
{
    public class CategoryWithItemDTO
    {
        public int Id { get; set; }
        public string Description { get; set; }
        public List<ItemDTO> Items { get; set; }
    }
}
