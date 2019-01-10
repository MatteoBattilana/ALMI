package utils;

import exceptions.BlockingRequestException;

public interface Interpreter<T>
{
    T interpret() throws BlockingRequestException;
}