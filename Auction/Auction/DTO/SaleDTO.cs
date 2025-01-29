using Auction.Models;

namespace Auction.DTO
{
    public class SaleDTO
    {
        public int Id { get; set; }
        public DateTime SaleDate { get; set; }
        public float SalePrice { get; set; }
        public int ItemId { get; set; }
        public string ItemName { get; set; }

    }
}

