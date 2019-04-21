package pingpong;

public class PingPong
{
    public enum State
    {
        PING("ping"),
        PONG("pong"),
        UNKNOWN("");

        private final String mState;

        State(String state)
        {
            mState = state;
        }

        @Override
        public String toString()
        {
            return mState;
        }
    }

    public State getReponse(State request)
    {
        switch(request)
        {
            case PING:
                return State.PONG;
            case PONG:
                return State.PING;
            default:
                return State.UNKNOWN;
        }
    }
}
