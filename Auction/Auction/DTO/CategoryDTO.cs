using Auction.Models;
using System.ComponentModel.DataAnnotations;

namespace Auction.DTO
{
    public class CategoryDTO
    {
        public int Id { get; set; }
        public string Description { get; set; }

        public List<ItemDTO> Items{ get; set; }

    }
}
