namespace Auction.DTO
{
    public class SaleDTOCreate
    {
        public int Id { get; set; }
        public DateTime SaleDate { get; set; }
        public float SalePrice { get; set; }
        public int ItemId { get; set; }
    }
}
