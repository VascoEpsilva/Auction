using System.ComponentModel.DataAnnotations;
using System.Runtime.CompilerServices;

namespace Auction.Models
{
    public class Sale
    {
        [Key]
        public int Id { get; set; }

        public DateTime SaleDate { get; set; }

        [Range(0, int.MaxValue)]
        public float SalePrice { get; set; }
        public int ItemId { get; set; }
        public Item Item { get; set; }
    }
}
