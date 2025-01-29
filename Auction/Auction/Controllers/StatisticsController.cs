using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Auction.DTO;
using Auction.Services;

namespace Auction.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class StatisticsController : ControllerBase
    {
        private readonly StatisticsService statisticsService;

        public StatisticsController(StatisticsService statisticsService)
        {
            this.statisticsService = statisticsService;
        }



        [HttpGet("total-items")]
        public async Task<ActionResult<int>> GetTotalItems()
        {
            return Ok(await statisticsService.GetTotalItemsAsync());
        }

        [HttpGet("total-available-items")]
        public async Task<ActionResult<int>> GetTotalAvailableItems()
        {
            return Ok(await statisticsService.GetTotalAvailableItemsAsync());
        }

        [HttpGet("total-sales")]
        public async Task<ActionResult<int>> GetTotalSales()
        {
            return Ok(await statisticsService.GetTotalSalesAsync());
        }

        [HttpGet("items-per-category/{categoryId}")]
        public async Task<ActionResult<int>> GetItemsCountByCategory(int categoryId)
        {
            return Ok(await statisticsService.GetItemsCountByCategoryIdAsync(categoryId));
        }

        [HttpGet("average-price")]
        public async Task<ActionResult<float>> GetAveragePrice()
        {
            return Ok(await statisticsService.GetAveragePriceAsync());
        }

        [HttpGet("average-price/category/{categoryId}")]
        public async Task<ActionResult<float>> GetAveragePriceByCategory(int categoryId)
        {
            return Ok(await statisticsService.GetAveragePriceByCategoryIdAsync(categoryId));
        }

        [HttpGet("cheapest-item-sold")]
        public async Task<ActionResult<SaleDTO>> GetCheapestItemSold()
        {
            var saleDto = await statisticsService.GetCheapestItemSoldAsync();
            if (saleDto == null) return NotFound("No sales found.");
            return Ok(saleDto);
        }

        [HttpGet("cheapest-sold/category/{categoryId}")]
        public async Task<ActionResult<SaleDTO>> GetCheapestSoldByCategory(int categoryId)
        {
            var saleDto = await statisticsService.GetCheapestSoldByCategoryAsync(categoryId);
            if (saleDto == null) return NotFound($"No sales found for category {categoryId}.");
            return Ok(saleDto);
        }

        [HttpGet("cheapest-sold-by-month")]
        public async Task<ActionResult<SaleDTO>> GetCheapestSoldByMonth([FromQuery] int year, [FromQuery] int month)
        {
            var saleDto = await statisticsService.GetCheapestSoldByMonthAsync(year, month);
            if (saleDto == null) return NotFound($"No sales found for {year}-{month:D2}.");
            return Ok(saleDto);
        }

        [HttpGet("most-expensive-item-sold")]
        public async Task<ActionResult<ItemDTO>> GetMostExpensiveItemSold()
        {
            var itemDto = await statisticsService.GetMostExpensiveItemSoldAsync();
            if (itemDto == null) return NotFound("No sales found.");
            return Ok(itemDto);
        }

        [HttpGet("most-expensive-item-sold/category/{categoryId}")]
        public async Task<ActionResult<ItemDTO>> GetMostExpensiveItemSoldByCategory(int categoryId)
        {
            var itemDto = await statisticsService.GetMostExpensiveItemSoldByCategoryIdAsync(categoryId);
            if (itemDto == null) return NotFound($"No sales found for category {categoryId}.");
            return Ok(itemDto);
        }

        [HttpGet("most-expensive-item-sold-by-month")]
        public async Task<ActionResult<ItemDTO>> GetMostExpensiveItemSoldByMonth([FromQuery] int year, [FromQuery] int month)
        {
            var itemDto = await statisticsService.GetMostExpensiveItemSoldByMonthAsync(year, month);
            if (itemDto == null) return NotFound($"No sales found for {year}-{month:D2}.");
            return Ok(itemDto);
        }
    }
}
