namespace Auction.DTO
{
    public class ItemDTO
    {

        public int Id { get; set; }

        public string Name { get; set; }

        public string Description { get; set; }

        public float Price { get; set; }

        public bool IsAvailable { get; set; } = true;

        public int CategoryId { get; set; }

        public string CategoryName { get; set; }

    }
}
