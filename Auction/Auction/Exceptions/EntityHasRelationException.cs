namespace Auction.Exceptions
{
    /// <summary>
    /// Exceção lançada quando a entidade possui relacionamentos e não pode ser removida.
    /// </summary>
    public class EntityHasRelationsException : Exception
    {
        public EntityHasRelationsException(string message) : base(message) { }
    }
}
