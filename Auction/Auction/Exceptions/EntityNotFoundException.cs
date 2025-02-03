namespace Auction.Exceptions
{
    /// <summary>
    /// Exceção lançada quando uma entidade (Category, Item, Sale, etc.) não é encontrada.
    /// </summary>
    public class EntityNotFoundException : Exception
    {
        public EntityNotFoundException(string message) : base(message) { }
    }
}
