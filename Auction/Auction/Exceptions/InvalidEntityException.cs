namespace Auction.Exceptions
{
    /// <summary>
    /// Exceção lançada quando dados de entrada estão inválidos para a lógica de negócio.
    /// </summary>
    public class InvalidEntityException : Exception
    {
        public InvalidEntityException(string message) : base(message) { }
    }
}
