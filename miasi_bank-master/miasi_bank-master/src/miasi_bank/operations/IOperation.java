package miasi_bank.operations;

import custom_exceptions.WrongValueException;

public interface IOperation {

    public double execute() throws WrongValueException;
}
