using Auction.Models;
using Microsoft.EntityFrameworkCore;

namespace Auction.Data
{
    public class AuctionContext : DbContext
    {
        public AuctionContext(DbContextOptions<AuctionContext> options) : base(options) { }

        public DbSet<Item> Items { get; set; }
        public DbSet<Category> Categories { get; set; }
        public DbSet<Sale> Sale { get; set; }

    }
}
