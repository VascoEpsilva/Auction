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
using Auction.Exceptions;

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
            var cat = await categoryService.GetAllCategoriesAsync();
            List<CategoryDTO> dtoList = new List<CategoryDTO>();
            foreach (var category in cat)
            {
                dtoList.Add(CategoryMapper.ToDTO(category));
            }

            return Ok(dtoList);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<CategoryDTO>> GetCategory(int id)
        {
            try
            {
                var categoryDto = CategoryMapper.ToDTO(await categoryService.GetCategoryAsync(id));
                return Ok(categoryDto);
            }
            catch (EntityNotFoundException)
            {
                return NotFound();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpGet("{id}/items")]
        public async Task<ActionResult<CategoryWithItemDTO>> GetCategoryWithItems(int id)
        {
            try
            {
                var categoryDto = CategoryMapper.ToDTOWithItem(await categoryService.GetCategoryWithItemsAsync(id));
                return Ok(categoryDto);
            }
            catch (EntityNotFoundException)
            {
                return NotFound();
            }
            catch (Exception)
            {
                return BadRequest();
            }
        }

        [HttpPut("{id}")]
        public async Task<IActionResult> PutCategory(int id, Category category)
        {
            try
            {
                var success = await categoryService.UpdateCategoryAsync(id, category);
                return NoContent();
            }
            catch (EntityNotFoundException)
            {
                return NotFound();
            }
            catch (Exception ex)
            {
                return BadRequest();
            }
        }

        [HttpPost]
        public async Task<ActionResult<CategoryDTO>> PostCategory(Category category)
        {
            var categoryDto = CategoryMapper.ToDTO(await categoryService.CreateCategoryAsync(category));
            return CreatedAtAction(nameof(GetCategory), new { id = categoryDto.Id }, categoryDto);
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteCategory(int id)
        {
            try
            {
                var success = await categoryService.DeleteCategoryAsync(id);
                return NoContent();
            }
            catch (EntityNotFoundException)
            {
                return NotFound();
            }
            catch (EntityHasRelationsException)
            {
                return BadRequest("Category contains Items");
            }
        }
    }

}
