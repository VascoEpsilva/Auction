using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Auction.Data;
using Auction.Models;
using Auction.DTO;
using Auction.Mappers;
using Microsoft.IdentityModel.Tokens;

namespace Auction.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CategoriesController : ControllerBase
    {
        private readonly CategoryService categoryService;

        public CategoriesController(CategoryService categoryService)
        {
            this.categoryService = categoryService;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<CategoryDTO>>> GetCategories()
        {
            return Ok(await categoryService.GetCategoriesAsync());
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<CategoryDTO>> GetCategory(int id)
        {
            var categoryDto = await categoryService.GetCategoryAsync(id);
            if (categoryDto == null) return NotFound();
            return Ok(categoryDto);
        }

        [HttpGet("{id}/items")]
        public async Task<ActionResult<CategoryWithItemDTO>> GetCategoryWithItems(int id)
        {
            var categoryDto = await categoryService.GetCategoryWithItemsAsync(id);
            if (categoryDto == null) return NotFound();
            return Ok(categoryDto);
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutCategory(int id, Category category)
        {
            var success = await categoryService.UpdateCategoryAsync(id, category);
            if (!success) return BadRequest("Category not found or invalid.");
            return NoContent();
        }

        [HttpPost]
        public async Task<ActionResult<CategoryDTO>> PostCategory(Category category)
        {
            var categoryDto = await categoryService.CreateCategoryAsync(category);
            return CreatedAtAction(nameof(GetCategory), new { id = categoryDto.Id }, categoryDto);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCategory(int id)
        {
            var success = await categoryService.DeleteCategoryAsync(id);
            if (!success) return BadRequest("Category not found or contains items.");
            return NoContent();
        }
    }

}
