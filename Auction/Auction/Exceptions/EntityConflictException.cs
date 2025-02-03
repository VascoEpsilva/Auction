namespace Auction.Exceptions
{
    /// <summary>
    /// Exceção lançada quando há conflito de IDs, por exemplo, ao atualizar.
    /// </summary>
    public class EntityConflictException : Exception
    {
        public EntityConflictException(string message) : base(message) { }
    }
}
