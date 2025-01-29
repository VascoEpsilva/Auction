using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Resources;

namespace Auction.Models
{
    public class Item
    {

        [Key]
        public int Id { get; set; }

        [Column(TypeName = "NVARCHAR(40)")]
        public string Name { get; set; }

        [Column(TypeName = "NVARCHAR(255)")]
        public string Description { get; set; }

        [Range(0, int.MaxValue)]
        public float Price { get; set; }

        public bool IsAvailable { get; set; } = true;


        public int CategoryId{ get; set; }

        public Category Category { get; set; }
    }
}
