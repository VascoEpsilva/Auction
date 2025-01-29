using Auction.DTO;
using Auction.Models;

namespace Auction.Mappers
{
    public class SaleMapper
    {
        public static SaleDTO ToDto(Sale sale)
        {
            return new SaleDTO
            {
                Id = sale.Id,
                SaleDate = sale.SaleDate,
                SalePrice = sale.SalePrice,
                ItemId = sale.Item.Id,
                ItemName = sale.Item.Name,

            };
        }

        public static Sale ToModel(SaleDTO saleDto)
        {
            return new Sale
            {
                Id = saleDto.Id,
                SaleDate = saleDto.SaleDate,
                SalePrice = saleDto.SalePrice,
                ItemId = saleDto.ItemId
            };
        }
    }
}
