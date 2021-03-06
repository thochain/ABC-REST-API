package com.thochain.abcapi.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 *
 */
public class ABCTokenContract extends Contract {
    private static final String BINARY = "60806040526000805460a060020a60ff021916905534801561002057600080fd5b5060405160808061071a833981016040908152815160208301519183015160609093015160008054600160a060020a0319908116331790915560048054600160a060020a03948516908316179055600580549484169482169490941793849055600294909455600655600180549093169116179055610676806100a46000396000f3006080604052600436106100ae5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166318160ddd81146100ba5780633f4ba83a146100e15780634172d0801461010a5780635c975abb1461011f5780635d452201146101345780636f7920fd14610165578063788ce6f21461017a5780638456cb591461018f5780638da5cb5b146101a4578063a81c3bdf146101b9578063f2fde38b146101ce575b6100b833346101ef565b005b3480156100c657600080fd5b506100cf61035a565b60408051918252519081900360200190f35b3480156100ed57600080fd5b506100f6610360565b604080519115158252519081900360200190f35b34801561011657600080fd5b506100cf6103db565b34801561012b57600080fd5b506100f66103e1565b34801561014057600080fd5b506101496103f1565b60408051600160a060020a039092168252519081900360200190f35b34801561017157600080fd5b506100cf610400565b34801561018657600080fd5b50610149610406565b34801561019b57600080fd5b506100f6610415565b3480156101b057600080fd5b50610149610495565b3480156101c557600080fd5b506101496104a4565b3480156101da57600080fd5b506100b8600160a060020a03600435166104b3565b60008054819081908190819060a060020a900460ff161561020f57600080fd5b6003546002541161021f57600080fd5b61022b86600654610505565b945061023960035486610530565b93508360025410156102fb5761025360025460035461054a565b925061025f858461054a565b6002546003556006549092508281151561027557fe5b049050610282878461055e565b151561028d57600080fd5b604051339082156108fc029083906000818181858888f193505050501580156102ba573d6000803e3d6000fd5b50600454604051600160a060020a0390911690303180156108fc02916000818181858888f193505050501580156102f5573d6000803e3d6000fd5b50610351565b600384905561030a878661055e565b151561031557600080fd5b600454604051600160a060020a0390911690303180156108fc02916000818181858888f1935050505015801561034f573d6000803e3d6000fd5b505b50505050505050565b60035481565b60008054600160a060020a0316331461037857600080fd5b60005460a060020a900460ff16151561039057600080fd5b6000805474ff0000000000000000000000000000000000000000191681556040517f7805862f689e2f13df9f062ff482ad3ad112aca9e0847911ed832e158c525b339190a150600190565b60065481565b60005460a060020a900460ff1681565b600154600160a060020a031681565b60025481565b600554600160a060020a031681565b60008054600160a060020a0316331461042d57600080fd5b60005460a060020a900460ff161561044457600080fd5b6000805474ff0000000000000000000000000000000000000000191660a060020a1781556040517f6985a02210a168e66602d3235cb6db0e70f92b3ba4d376a33c0f3d9434bff6259190a150600190565b600054600160a060020a031681565b600454600160a060020a031681565b600054600160a060020a031633146104ca57600080fd5b600160a060020a03811615610502576000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383161790555b50565b6000828202831580610521575082848281151561051e57fe5b04145b151561052957fe5b9392505050565b600082820183811080159061052157508281101561052957fe5b6000808284101561055757fe5b5050900390565b604080516000808252600160a060020a038516602083015281830184905291517f6ffa1d489045d96c2691a9c911b5cd15308401f6c62def7cf8f32653d8d70b249181900360600190a1600154604080517f6c197ff5000000000000000000000000000000000000000000000000000000008152600160a060020a0386811660048301526024820186905291519190921691636c197ff59160448083019260209291908290030181600087803b15801561061757600080fd5b505af115801561062b573d6000803e3d6000fd5b505050506040513d602081101561064157600080fd5b505193925050505600a165627a7a723058201c6b6d46202e79ce0d4d2602c123cda3de1232eb50d6603f644645a897f95a780029";

    /**
     *
     * @param contractAddress
     * @param web3j
     * @param transactionManager
     * @param gasPrice
     * @param gasLimit
     */
    protected ABCTokenContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    /**
     *
     * @return
     */
    public RemoteCall<BigInteger> totalSupply() {
        Function function = new Function("totalSupply", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    /**
     *
     * @return
     */
    public RemoteCall<TransactionReceipt> enableTrading() {
        Function function = new Function(
                "enableTrading",
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    /**
     *
     * @return
     */
    public RemoteCall<TransactionReceipt> disableTrading() {
        Function function = new Function(
                "disableTrading",
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    /**
     *
     * @return
     */
    public RemoteCall<BigInteger> tokenCreationCap() {
        Function function = new Function("tokenCreationCap", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    /**
     *
     * @return
     */
    public RemoteCall<String> icoAddress() {
        Function function = new Function("icoAddress", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    /**
     *
     * @return
     */
    public RemoteCall<TransactionReceipt> pause() {
        Function function = new Function(
                "pause",
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    /**
     *
     * @return
     */
    public RemoteCall<TransactionReceipt> unpause() {
        Function function = new Function(
                "unpause",
                Arrays.<Type>asList(),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    /**
     *
     * @return
     */
    public RemoteCall<BigInteger> totalEtherHasBeenReceived() {
        Function function = new Function("totalEtherHasBeenReceived",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    /**
     *
     * @param transactionReceipt
     * @return
     */
    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("Approval",
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<EventValues> valueList = extractEventParameters(event, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (EventValues eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    /**
     *
     * @param contractAddress
     * @param web3j
     * @param transactionManager
     * @param gasPrice
     * @param gasLimit
     * @return
     */
    public static ABCTokenContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ABCTokenContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    /**
     *
     */
    public static class TransferEventResponse {
        public String _from;

        public String _to;

        public BigInteger _value;
    }

    /**
     *
     */
    public static class ApprovalEventResponse {
        public String _owner;

        public String _spender;

        public BigInteger _value;
    }
}
