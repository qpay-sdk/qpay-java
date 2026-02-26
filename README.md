# QPay Java SDK

[![Maven Central](https://img.shields.io/maven-central/v/io.github.qpay-sdk/qpay-java)](https://central.sonatype.com/artifact/io.github.qpay-sdk/qpay-java)
[![CI](https://github.com/qpay-sdk/qpay-java/actions/workflows/ci.yml/badge.svg)](https://github.com/qpay-sdk/qpay-java/actions/workflows/ci.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

Java SDK for the QPay V2 API. Provides a type-safe client with automatic token management, builder-pattern request construction, and comprehensive error handling.

## Requirements

- Java 17+
- Maven or Gradle

## Installation

### Maven

```xml
<dependency>
    <groupId>io.github.qpay-sdk</groupId>
    <artifactId>qpay-java</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```groovy
implementation 'io.github.qpay-sdk:qpay-java:1.0.0'
```

### Gradle (Kotlin DSL)

```kotlin
implementation("io.github.qpay-sdk:qpay-java:1.0.0")
```

## Quick Start

```java
import mn.qpay.sdk.*;
import mn.qpay.sdk.models.*;

// Configure the client
QPayConfig config = QPayConfig.builder()
        .baseURL("https://merchant.qpay.mn")
        .username("YOUR_USERNAME")
        .password("YOUR_PASSWORD")
        .invoiceCode("YOUR_INVOICE_CODE")
        .callbackURL("https://yoursite.com/payment/callback")
        .build();

// Create the client
QPayClient client = new QPayClient(config);

// Create an invoice
InvoiceResponse invoice = client.createSimpleInvoice(
        CreateSimpleInvoiceRequest.builder()
                .invoiceCode(config.getInvoiceCode())
                .senderInvoiceNo("ORDER-001")
                .invoiceDescription("Payment for Order #001")
                .amount(50000.0)
                .callbackURL(config.getCallbackURL())
                .build()
);

System.out.println("Invoice ID: " + invoice.getInvoiceId());
System.out.println("QR Image: " + invoice.getQrImage());
System.out.println("Short URL: " + invoice.getQPayShortUrl());
```

## Configuration

### Builder Pattern

```java
QPayConfig config = QPayConfig.builder()
        .baseURL("https://merchant.qpay.mn")
        .username("YOUR_USERNAME")
        .password("YOUR_PASSWORD")
        .invoiceCode("YOUR_INVOICE_CODE")
        .callbackURL("https://yoursite.com/payment/callback")
        .build();
```

All fields are required. The builder throws `IllegalArgumentException` if any field is null or blank.

### Environment Variables

```java
QPayConfig config = QPayConfig.fromEnv();
```

Required environment variables:

| Variable | Description |
|---|---|
| `QPAY_BASE_URL` | QPay API base URL (e.g., `https://merchant.qpay.mn`) |
| `QPAY_USERNAME` | QPay merchant username |
| `QPAY_PASSWORD` | QPay merchant password |
| `QPAY_INVOICE_CODE` | Default invoice code |
| `QPAY_CALLBACK_URL` | Payment callback URL |

Throws `QPayException` if any required variable is missing.

## Token Management

The SDK handles token lifecycle automatically:

- **Auto-authentication**: The first API call triggers authentication via `POST /v2/auth/token`.
- **Token reuse**: Valid access tokens are reused across requests.
- **Auto-refresh**: Expired access tokens are refreshed using the refresh token via `POST /v2/auth/refresh`.
- **Fallback**: If refresh fails, the SDK re-authenticates with username/password.
- **Thread safety**: Token access is synchronized for safe concurrent use.

You can also manage tokens manually:

```java
// Manually get a token
TokenResponse token = client.getToken();

// Manually refresh
TokenResponse refreshed = client.refreshToken();
```

## Usage Examples

### Create Invoice (Full Options)

```java
import mn.qpay.sdk.models.common.*;

CreateInvoiceRequest request = CreateInvoiceRequest.builder()
        .invoiceCode("YOUR_INVOICE_CODE")
        .senderInvoiceNo("ORDER-002")
        .invoiceDescription("Detailed order payment")
        .amount(100000.0)
        .callbackURL("https://yoursite.com/payment/callback")
        .allowPartial(false)
        .allowExceed(false)
        .note("VIP customer order")
        .senderBranchCode("BRANCH_01")
        .senderBranchData(
                CreateInvoiceRequest.SenderBranchData.builder()
                        .name("Main Branch")
                        .email("branch@company.mn")
                        .phone("77001122")
                        .address(Address.builder()
                                .city("Ulaanbaatar")
                                .district("Sukhbaatar")
                                .street("Peace Avenue 22")
                                .build())
                        .build()
        )
        .invoiceReceiverData(
                CreateInvoiceRequest.InvoiceReceiverData.builder()
                        .register("AA12345678")
                        .name("Customer Name")
                        .email("customer@mail.com")
                        .phone("99112233")
                        .build()
        )
        .lines(List.of(
                InvoiceLine.builder()
                        .taxProductCode("TAX001")
                        .lineDescription("Product A")
                        .lineQuantity("2")
                        .lineUnitPrice("50000")
                        .build()
        ))
        .build();

InvoiceResponse invoice = client.createInvoice(request);

System.out.println("Invoice ID: " + invoice.getInvoiceId());
System.out.println("QR Text: " + invoice.getQrText());

// Show available payment deeplinks
for (Deeplink url : invoice.getUrls()) {
    System.out.println(url.getName() + ": " + url.getLink());
}
```

### Create Simple Invoice

```java
CreateSimpleInvoiceRequest request = CreateSimpleInvoiceRequest.builder()
        .invoiceCode("YOUR_INVOICE_CODE")
        .senderInvoiceNo("ORDER-003")
        .invoiceDescription("Simple payment")
        .amount(25000.0)
        .callbackURL("https://yoursite.com/payment/callback")
        .build();

InvoiceResponse invoice = client.createSimpleInvoice(request);
```

### Cancel Invoice

```java
client.cancelInvoice("INVOICE_ID");
```

### Check Payment

```java
import mn.qpay.sdk.models.common.Offset;

PaymentCheckRequest request = PaymentCheckRequest.builder()
        .objectType("INVOICE")
        .objectId("INVOICE_ID")
        .offset(new Offset(1, 10))
        .build();

PaymentCheckResponse response = client.checkPayment(request);

System.out.println("Payment count: " + response.getCount());
System.out.println("Paid amount: " + response.getPaidAmount());

for (PaymentCheckResponse.PaymentCheckRow row : response.getRows()) {
    System.out.println("Payment " + row.getPaymentId() + ": " + row.getPaymentStatus());
}
```

### Get Payment Details

```java
PaymentDetail detail = client.getPayment("PAYMENT_ID");

System.out.println("Status: " + detail.getPaymentStatus());
System.out.println("Amount: " + detail.getPaymentAmount());
System.out.println("Currency: " + detail.getPaymentCurrency());
System.out.println("Wallet: " + detail.getPaymentWallet());
```

### List Payments

```java
PaymentListRequest request = PaymentListRequest.builder()
        .objectType("INVOICE")
        .objectId("INVOICE_ID")
        .startDate("2024-01-01")
        .endDate("2024-12-31")
        .offset(new Offset(1, 20))
        .build();

PaymentListResponse response = client.listPayments(request);

System.out.println("Total payments: " + response.getCount());
for (PaymentListResponse.PaymentListItem item : response.getRows()) {
    System.out.println(item.getPaymentId() + " - " + item.getPaymentAmount() + " MNT");
}
```

### Cancel Payment

```java
client.cancelPayment("PAYMENT_ID", "https://yoursite.com/cancel-callback", "Customer requested cancellation");
```

### Refund Payment

```java
client.refundPayment("PAYMENT_ID", "https://yoursite.com/refund-callback", "Product returned");
```

### Create Ebarimt (Electronic Tax Receipt)

```java
CreateEbarimtRequest request = CreateEbarimtRequest.builder()
        .paymentId("PAYMENT_ID")
        .ebarimtReceiverType("individual")
        .ebarimtReceiver("AA12345678")
        .districtCode("01")
        .classificationCode("CL001")
        .build();

EbarimtResponse ebarimt = client.createEbarimt(request);

System.out.println("Ebarimt ID: " + ebarimt.getId());
System.out.println("Status: " + ebarimt.getBarimtStatus());
System.out.println("QR Data: " + ebarimt.getEbarimtQrData());
System.out.println("Lottery: " + ebarimt.getEbarimtLottery());
```

### Cancel Ebarimt

```java
EbarimtResponse cancelled = client.cancelEbarimt("PAYMENT_ID");
System.out.println("Cancelled status: " + cancelled.getBarimtStatus());
```

## Error Handling

All API errors throw `QPayException`, which is a `RuntimeException`:

```java
try {
    client.createSimpleInvoice(request);
} catch (QPayException e) {
    System.err.println("Status code: " + e.getStatusCode());
    System.err.println("Error code: " + e.getCode());
    System.err.println("Message: " + e.getMessage());
    System.err.println("Raw body: " + e.getRawBody());
}
```

### Error Code Constants

Use `ErrorCodes` constants for reliable error matching:

```java
try {
    client.getPayment("invalid-id");
} catch (QPayException e) {
    if (ErrorCodes.PAYMENT_NOTFOUND.equals(e.getCode())) {
        System.out.println("Payment does not exist");
    } else if (ErrorCodes.AUTHENTICATION_FAILED.equals(e.getCode())) {
        System.out.println("Authentication failed, check credentials");
    } else {
        System.out.println("Unexpected error: " + e);
    }
}
```

### Common Error Codes

| Code | Description |
|---|---|
| `AUTHENTICATION_FAILED` | Invalid username/password |
| `INVOICE_NOTFOUND` | Invoice does not exist |
| `INVOICE_PAID` | Invoice already paid |
| `INVOICE_ALREADY_CANCELED` | Invoice already cancelled |
| `INVOICE_CODE_INVALID` | Invalid invoice code |
| `PAYMENT_NOTFOUND` | Payment does not exist |
| `PAYMENT_NOT_PAID` | Payment not yet completed |
| `PAYMENT_ALREADY_CANCELED` | Payment already cancelled |
| `INVALID_AMOUNT` | Invalid payment amount |
| `PERMISSION_DENIED` | Insufficient permissions |
| `MERCHANT_NOTFOUND` | Merchant does not exist |
| `MERCHANT_INACTIVE` | Merchant account is inactive |
| `EBARIMT_NOT_REGISTERED` | Ebarimt not registered |

### Utility: asQPayError

```java
try {
    // some operation that might throw
} catch (Exception e) {
    QPayException qe = QPayException.asQPayError(e);
    if (qe != null) {
        // Handle QPay-specific error
    } else {
        // Handle other errors
    }
}
```

## API Reference

### QPayClient Methods

| Method | HTTP | Path | Description |
|---|---|---|---|
| `getToken()` | POST | `/v2/auth/token` | Authenticate and get token pair |
| `refreshToken()` | POST | `/v2/auth/refresh` | Refresh access token |
| `createInvoice(CreateInvoiceRequest)` | POST | `/v2/invoice` | Create invoice with full options |
| `createSimpleInvoice(CreateSimpleInvoiceRequest)` | POST | `/v2/invoice` | Create invoice with minimal fields |
| `cancelInvoice(String)` | DELETE | `/v2/invoice/{id}` | Cancel an invoice |
| `getPayment(String)` | GET | `/v2/payment/{id}` | Get payment details |
| `checkPayment(PaymentCheckRequest)` | POST | `/v2/payment/check` | Check payment status |
| `listPayments(PaymentListRequest)` | POST | `/v2/payment/list` | List payments |
| `cancelPayment(String, String, String)` | DELETE | `/v2/payment/cancel/{id}` | Cancel a card payment |
| `refundPayment(String, String, String)` | DELETE | `/v2/payment/refund/{id}` | Refund a card payment |
| `createEbarimt(CreateEbarimtRequest)` | POST | `/v2/ebarimt_v3/create` | Create electronic tax receipt |
| `cancelEbarimt(String)` | DELETE | `/v2/ebarimt_v3/{id}` | Cancel electronic tax receipt |

### Custom HttpClient

You can provide a custom `HttpClient` for advanced configuration (proxy, timeouts, etc.):

```java
HttpClient customHttp = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .followRedirects(HttpClient.Redirect.NORMAL)
        .build();

QPayClient client = new QPayClient(config, customHttp);
```

## Testing

```bash
mvn test
```

The test suite uses JUnit 5 and OkHttp MockWebServer to test all client methods, error handling, and automatic token management without requiring a live QPay API connection.

## License

MIT License - see [LICENSE](LICENSE) for details.
