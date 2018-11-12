package com.thochain.abcapi;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import com.thochain.abcapi.contract.ABCTokenContract;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;

import static org.web3j.tx.Contract.GAS_LIMIT;
import static org.web3j.tx.ManagedTransaction.GAS_PRICE;

/**
 * Our smart contract service.
 */
@Service
public class ContractService {

    private final Quorum quorum;

    private final NodeConfiguration nodeConfiguration;

    @Autowired
    public ContractService(Quorum quorum, NodeConfiguration nodeConfiguration) {
        this.quorum = quorum;
        this.nodeConfiguration = nodeConfiguration;
    }

    /**
     *
     * @return
     */
    public NodeConfiguration getConfig() {
        return nodeConfiguration;
    }

    /**
     *
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public TransactionResponse<ApprovalEventResponse> enableTrading(String contractAddress) throws Exception {
        ABCTokenContract token = load(contractAddress);
        try {
            TransactionReceipt transactionReceipt = token.enableTrading().send();
            return processApprovalEventResponse(token, transactionReceipt);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public TransactionResponse<ApprovalEventResponse> startTokenSale(String contractAddress) throws Exception {
        ABCTokenContract token = load(contractAddress);
        try {
            TransactionReceipt transactionReceipt = token.unpause().send();
            return processApprovalEventResponse(token, transactionReceipt);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public TransactionResponse<ApprovalEventResponse> stopTokenSale(String contractAddress) throws Exception {
        ABCTokenContract token = load(contractAddress);
        try {
            TransactionReceipt transactionReceipt = token.pause().send();
            return processApprovalEventResponse(token, transactionReceipt);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public TransactionResponse<ApprovalEventResponse> disableTrading(String contractAddress) throws Exception {
        ABCTokenContract token = load(contractAddress);
        try {
            TransactionReceipt transactionReceipt = token.disableTrading().send();
            return processApprovalEventResponse(token, transactionReceipt);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public long totalSupply(String contractAddress) throws Exception {
        ABCTokenContract token = load(contractAddress);
        try {
            return extractLongValue(token.totalSupply().send());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param contractAddress
     * @return
     * @throws Exception
     */
    public long tokenCreationCap(String contractAddress) throws Exception {
        ABCTokenContract token = load(contractAddress);
        try {
            return extractLongValue(token.tokenCreationCap().send());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param contractAddress
     * @param privateFor
     * @return
     */
    private ABCTokenContract load(String contractAddress, List<String> privateFor) {
        TransactionManager transactionManager = new ClientTransactionManager(
                quorum, nodeConfiguration.getFromAddress(), privateFor);
        return ABCTokenContract.load(
                contractAddress, quorum, transactionManager, GAS_PRICE, GAS_LIMIT);
    }

    /**
     *
     * @param contractAddress
     * @return
     */
    private ABCTokenContract load(String contractAddress) {
        TransactionManager transactionManager = new ClientTransactionManager(
                quorum, nodeConfiguration.getFromAddress(), Collections.emptyList());
        return ABCTokenContract.load(
                contractAddress, quorum, transactionManager, GAS_PRICE, GAS_LIMIT);
    }

    /**
     *
     * @param value
     * @return
     */
    private long extractLongValue(BigInteger value) {
        try {
            return value.longValueExact();
        } catch (Exception e) {
            return Long.parseLong(value.divide(new BigInteger("1000000000000000000")).toString());
        }
    }

    /**
     *
     * @param humanStandardToken
     * @param transactionReceipt
     * @return
     */
    private TransactionResponse<ApprovalEventResponse>
            processApprovalEventResponse(
            ABCTokenContract humanStandardToken,
            TransactionReceipt transactionReceipt) {

        return processEventResponse(
                humanStandardToken.getApprovalEvents(transactionReceipt),
                transactionReceipt,
                ApprovalEventResponse::new);
    }

    /**
     *
     * @param eventResponses
     * @param transactionReceipt
     * @param map
     * @param <T>
     * @param <R>
     * @return
     */
    private <T, R> TransactionResponse<R> processEventResponse(
            List<T> eventResponses, TransactionReceipt transactionReceipt, Function<T, R> map) {
        if (!eventResponses.isEmpty()) {
            return new TransactionResponse<>(
                    transactionReceipt.getTransactionHash(),
                    map.apply(eventResponses.get(0)));
        } else {
            return new TransactionResponse<>(
                    transactionReceipt.getTransactionHash());
        }
    }

    @Getter
    @Setter
    public static class TransferEventResponse {
        private String from;
        private String to;
        private long value;

        public TransferEventResponse() { }

        public TransferEventResponse(
                ABCTokenContract.TransferEventResponse transferEventResponse) {
            this.from = transferEventResponse._from;
            this.to = transferEventResponse._to;
            this.value = transferEventResponse._value.longValueExact();
        }
    }

    @Getter
    @Setter
    public static class ApprovalEventResponse {
        private String owner;
        private String spender;
        private long value;

        public ApprovalEventResponse() { }

        public ApprovalEventResponse(
                ABCTokenContract.ApprovalEventResponse approvalEventResponse) {
            this.owner = approvalEventResponse._owner;
            this.spender = approvalEventResponse._spender;
            this.value = approvalEventResponse._value.longValueExact();
        }
    }
}
