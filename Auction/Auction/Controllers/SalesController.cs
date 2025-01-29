using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Auction.DTO;
using Auction.Services;

namespace Auction.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SalesController : ControllerBase
    {
        private readonly SaleService saleService;

        public SalesController(SaleService saleService)
        {
            this.saleService = saleService;
        }

        [HttpGet]
        public async Task<ActionResult<List<SaleDTO>>> GetAllSales()
        {
            var sales = await saleService.GetAllSaleAsync();
            return Ok(sales);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<SaleDTO>> GetSaleById(int id)
        {
            var sale = await saleService.GetSaleByIdAsync(id);
            if (sale == null) return NotFound("Sale not found.");
            return Ok(sale);
        }

        [HttpGet("price-range")]
        public async Task<ActionResult<List<SaleDTO>>> GetSalesByPriceRange([FromQuery] float min, [FromQuery] float max)
        {
            var sales = await saleService.GetSaleByPriceRangeAsync(min, max);
            return Ok(sales);
        }

        [HttpGet("item/{itemId}")]
        public async Task<ActionResult<List<SaleDTO>>> GetSalesByItemId(int itemId)
        {
            var sales = await saleService.GetSaleByItemIdAsync(itemId);
            return Ok(sales);
        }

        [HttpGet("date")]
        public async Task<ActionResult<List<SaleDTO>>> GetSalesByDate([FromQuery] DateTime d)
        {
            var sales = await saleService.GetSaleByDateAsync(d);
            return Ok(sales);
        }

        [HttpGet("dates")]
        public async Task<ActionResult<List<SaleDTO>>> GetSalesBetweenDates([FromQuery] DateTime start, [FromQuery] DateTime end)
        {
            var sales = await saleService.GetSaleBetweenDatesAsync(start, end);
            return Ok(sales);
        }

        [HttpPost]
        public async Task<ActionResult<SaleDTO>> CreateSale([FromBody] SaleDTO saleDto)
        {
            try
            {
                var newSale = await saleService.CreateSaleAsync(saleDto);
                return CreatedAtAction(nameof(GetSaleById), new { id = newSale.Id }, newSale);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPut("{id}")]
        public async Task<ActionResult<SaleDTO>> UpdateSale(int id, [FromBody] SaleDTO saleDto)
        {
            try
            {
                var updatedSale = await saleService.UpdateSaleAsync(id, saleDto);
                if (updatedSale == null) return NotFound("Sale not found or ID mismatch.");
                return Ok(updatedSale);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteSale(int id)
        {
            var deleted = await saleService.DeleteSaleAsync(id);
            if (!deleted) return NotFound("Sale not found.");
            return NoContent();
        }
    }
}
