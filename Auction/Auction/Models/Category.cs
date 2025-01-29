using System.ComponentModel.DataAnnotations;

namespace Auction.Models
{
    public class Category
    {
        [Key]
        public int Id { get; set; }

        [MaxLength(100)]
        public string Description { get; set; }

        public ICollection<Item> Items { get; set; } = new List<Item>();

    }
}
