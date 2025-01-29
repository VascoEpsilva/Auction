using Auction.Data;
using Auction.DTO;
using Auction.Mappers;
using Auction.Models;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

public class CategoryService
{
    private readonly AuctionContext context;

    public CategoryService(AuctionContext context)
    {
        this.context = context;
    }

    public async Task<IEnumerable<CategoryDTO>> GetCategoriesAsync()
    {
        var categories = await context.Categories.ToListAsync();
        return categories.Select(CategoryMapper.ToDTO);
    }

    public async Task<CategoryDTO> GetCategoryAsync(int id)
    {
        var category = await context.Categories.FirstOrDefaultAsync(c => c.Id == id);
        if (category == null) return null;

        await context.Entry(category).Collection(c => c.Items).LoadAsync();

        return CategoryMapper.ToDTO(category);
    }

    public async Task<CategoryWithItemDTO> GetCategoryWithItemsAsync(int id)
    {
        var category = await context.Categories.FirstOrDefaultAsync(c => c.Id == id);
        if (category == null) return null;

        
        await context.Entry(category).Collection(c => c.Items).LoadAsync();

        return CategoryMapper.ToDTOWithItem(category);
    }

    public async Task<bool> UpdateCategoryAsync(int id, Category category)
    {
        if (id != category.Id) return false;

        context.Entry(category).State = EntityState.Modified;

        try
        {
            await context.SaveChangesAsync();
            return true;
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!await CategoryExistsAsync(id)) return false;
            throw;
        }
    }

    public async Task<CategoryDTO> CreateCategoryAsync(Category category)
    {
        context.Categories.Add(category);
        await context.SaveChangesAsync();

        return CategoryMapper.ToDTO(category);
    }

    public async Task<bool> DeleteCategoryAsync(int id)
    {
        var category = await context.Categories.Include(c => c.Items).FirstOrDefaultAsync(c => c.Id == id);
        if (category == null) return false;

        if (category.Items.Any()) return false; 

        context.Categories.Remove(category);
        await context.SaveChangesAsync();
        return true;
    }

    private async Task<bool> CategoryExistsAsync(int id)
    {
        return await context.Categories.AnyAsync(e => e.Id == id);
    }
}
