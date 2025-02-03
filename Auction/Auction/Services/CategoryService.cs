using Auction.Data;
using Auction.DTO;
using Auction.Exceptions;
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

    public async Task<IEnumerable<Category>> GetAllCategoriesAsync()
    {
        var categories = await context.Categories.ToListAsync();
        return categories;
    }

    public async Task<Category> GetCategoryAsync(int id)
    {
        var category = await context.Categories.FirstOrDefaultAsync(c => c.Id == id);
        if (category == null)
        {
            throw new EntityNotFoundException("Category not found");
        }

        await context.Entry(category).Collection(c => c.Items).LoadAsync();

        return category;
    }

    public async Task<Category> GetCategoryWithItemsAsync(int id)
    {
        var category = await context.Categories.FirstOrDefaultAsync(c => c.Id == id);
        if (category == null) return null;

        
        await context.Entry(category).Collection(c => c.Items).LoadAsync();

        return category;
    }

    public async Task<bool> UpdateCategoryAsync(int id, Category category)
    {
        if (id != category.Id)
        {
            throw new EntityNotFoundException("Id Not found");
        }

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

    public async Task<Category> CreateCategoryAsync(Category category)
    {
        context.Categories.Add(category);
        await context.SaveChangesAsync();

        return category;
    }

    public async Task<bool> DeleteCategoryAsync(int id)
    {
        var category = await context.Categories.Include(c => c.Items).FirstOrDefaultAsync(c => c.Id == id);
        if (category == null)
        {
            throw new EntityNotFoundException("Id not Found");
        }
        if (category.Items.Any())
        {
            throw new EntityHasRelationsException("Category has items and cannot be deleted.");
        }

        context.Categories.Remove(category);
        await context.SaveChangesAsync();
        return true;
    }

    private async Task<bool> CategoryExistsAsync(int id)
    {
        return await context.Categories.AnyAsync(e => e.Id == id);
    }
}
