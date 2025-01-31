﻿using Auction.DTO;
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

        public static Sale ToModelCreate(SaleDTOCreate dtoCreate)
        {
            if (dtoCreate == null) return null;

            return new Sale
            {
                // Often the Id is generated by the DB, so you might skip assigning dtoCreate.Id
                Id = dtoCreate.Id,
                SaleDate = dtoCreate.SaleDate,
                SalePrice = dtoCreate.SalePrice,
                ItemId = dtoCreate.ItemId
            };
        }

        public static SaleDTOCreate ToDtoCreate(Sale sale)
        {
            if (sale == null) return null;

            return new SaleDTOCreate
            {
                Id = sale.Id,
                SaleDate = sale.SaleDate,
                SalePrice = sale.SalePrice,
                ItemId = sale.ItemId
            };
        }

        public static ItemDTO ConvertSaleToItemDto(Sale sale)
        {
            var item = sale.Item;
            if (item == null)
            {
                return null;
            }

            return new ItemDTO
            {
                Id = item.Id,
                Name = item.Name,
                Description = item.Description,
                Price = item.Price,
                IsAvailable = item.IsAvailable,
                CategoryId = item.CategoryId,
                CategoryName = item.Category?.Description
            };
        }

    }
}
