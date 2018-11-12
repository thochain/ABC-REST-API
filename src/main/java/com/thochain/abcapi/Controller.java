package com.thochain.abcapi;

import java.math.BigInteger;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for our ERC-20 contract API.
 */
@Api("ERC-20 token standard API")
@RestController
public class Controller {

    private final ContractService ContractService;

    @Autowired
    public Controller(ContractService ContractService) {
        this.ContractService = ContractService;
    }

    @ApiOperation("Application configuration")
    @RequestMapping(value = "/config", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    NodeConfiguration config() {
        return ContractService.getConfig();
    }

    @ApiOperation("Enable Trading")
    @RequestMapping(value = "/{contractAddress}/enableTrading", method = RequestMethod.GET)
    TransactionResponse<ContractService.ApprovalEventResponse> enableTrading(@PathVariable String contractAddress) throws Exception {
        return ContractService.enableTrading(contractAddress);
    }

    @ApiOperation("Disable Trading")
    @RequestMapping(value = "/{contractAddress}/disableTrading", method = RequestMethod.GET)
    TransactionResponse<ContractService.ApprovalEventResponse> disableTrading(@PathVariable String contractAddress) throws Exception {
        return ContractService.disableTrading(contractAddress);
    }

    @ApiOperation("Stop Token Sale")
    @RequestMapping(value = "/{contractAddress}/stopTokenSale", method = RequestMethod.GET)
    TransactionResponse<ContractService.ApprovalEventResponse> stopTokenSale(@PathVariable String contractAddress) throws Exception {
        return ContractService.stopTokenSale(contractAddress);
    }

    @ApiOperation("Start Token Sale")
    @RequestMapping(value = "/{contractAddress}/startTokenSale", method = RequestMethod.GET)
    TransactionResponse<ContractService.ApprovalEventResponse> startTokenSale(@PathVariable String contractAddress) throws Exception {
        return ContractService.startTokenSale(contractAddress);
    }

    @ApiOperation("Get total supply of tokens")
    @RequestMapping(value = "/{contractAddress}/totalSupply", method = RequestMethod.GET)
    long totalSupply(@PathVariable String contractAddress) throws Exception {
        return ContractService.totalSupply(contractAddress);
    }

    @ApiOperation("Get token creation capital")
    @RequestMapping(value = "/{contractAddress}/tokenCreationCap", method = RequestMethod.GET)
    long tokenCreationCap(@PathVariable String contractAddress) throws Exception {
        return ContractService.tokenCreationCap(contractAddress);
    }

    @Data
    static class ContractSpecification {
        private final BigInteger initialAmount;
        private final String tokenName;
        private final BigInteger decimalUnits;
        private final String tokenSymbol;
    }

    @Data
    static class ApproveRequest {
        private final String spender;
        private final BigInteger value;
    }

    @Data
    static class TransferFromRequest {
        private final String from;
        private final String to;
        private final BigInteger value;
    }

    @Data
    static class TransferRequest {
        private final String to;
        private final BigInteger value;
    }

    @Data
    static class ApproveAndCallRequest {
        private final String spender;
        private final BigInteger value;
        private final String extraData;
    }

    @Data
    static class AllowanceRequest {
        private final String ownerAddress;
        private final String spenderAddress;
    }
}
