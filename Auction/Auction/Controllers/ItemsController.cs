using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Auction.DTO;
using Auction.Models;
using Auction.Services;

namespace Auction.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ItemsController : ControllerBase
    {
        private readonly ItemService itemService;

        public ItemsController(ItemService itemService)
        {
            this.itemService = itemService;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<ItemDTO>>> GetItems()
        {
            var items = await itemService.GetAllItemsAsync();
            return Ok(items);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<ItemDTO>> GetItem(int id)
        {
            var itemDto = await itemService.GetItemByIdAsync(id);
            if (itemDto == null)
            {
                return NotFound();
            }
            return Ok(itemDto);
        }

        [HttpGet("available")]
        public async Task<ActionResult<IEnumerable<ItemDTO>>> GetAvailableItems()
        {
            var items = await itemService.GetAvailableItemsAsync();
            return Ok(items);
        }

        [HttpGet("price-range")]
        public async Task<ActionResult<IEnumerable<ItemDTO>>> GetItemsByPriceRange([FromQuery] float min, [FromQuery] float max)
        {
            var items = await itemService.GetItemsByPriceRangeAsync(min, max);
            return Ok(items);
        }

        [HttpPost]
        public async Task<ActionResult<ItemDTO>> PostItem([FromBody] Item item)
        {
            var itemDto = await itemService.CreateItemAsync(item);
            return CreatedAtAction(nameof(GetItem), new { id = itemDto.Id }, itemDto);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutItem(int id, [FromBody] Item item)
        {
            var success = await itemService.UpdateItemAsync(id, item);
            if (!success)
            {
                return BadRequest("Item not found or invalid.");
            }
            return NoContent();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteItem(int id)
        {
            var success = await itemService.DeleteItemAsync(id);
            if (!success)
            {
                return BadRequest("Item not found or cannot be deleted.");
            }
            return NoContent();
        }
    }
}
