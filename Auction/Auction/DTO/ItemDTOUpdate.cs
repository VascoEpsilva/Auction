namespace Auction.DTO
{
    public class ItemDTOUpdate
    {
        public int Id { get; set; }

        public string Name { get; set; }

        public string Description { get; set; }

        public float Price { get; set; }

        public bool IsAvailable { get; set; }

        public int CategoryId { get; set; }

    }
}
