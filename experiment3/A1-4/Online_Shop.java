import java.util.*;
import java.time.*;

// ==========================
// 客户类 Customer
// ==========================
public class Customer {

    // 属性
    private String name;
    private String contact;
    private String deliveryAddress;
    private boolean active;

    // 多重性：Customer --> 0..* Order
    private List<Order> orders;

}


// ==========================
// 订单类 Order
// ==========================
public class Order {

    // 属性
    private LocalDateTime createDate;
    private enum Status {
        CREATE, PAID, SHIPPING, DELIVERED, CANCEL
    }
    private Status status;

    // 多重性：Order --> 1 Customer
    private Customer customer;
    // 多重性：Order --> 1..* OrderDetail
    private List<OrderDetail> orderDetails;
    // 多重性：Order --> 1 Payment
    private Payment payment;

    // 构造器，确保 Order 创建时满足以上约束
    public Order(Customer customer, List<OrderDetail> orderDetails, Payment payment) {
        if (customer == null)
            throw new IllegalArgumentException("Order 必须属于一个 Customer");
        if (orderDetails == null || orderDetails.isEmpty())
            throw new IllegalArgumentException("Order 必须至少包含一个 OrderDetail");
        if (payment == null)
            throw new IllegalArgumentException("Order 必须关联一个 Payment 支付记录");

        this.customer = customer;
        this.orderDetails = orderDetails;
        this.payment = payment;
    }

    // 用于实现组合关系的工厂方法：以自身作为绑定目标，创建 OrderDetail 对象
    public OrderDetail createOrderDetail(Product product) {
        OrderDetail orderdetail = new OrderDetail(this, product);
        orderDetails.add(orderdetail);
        return orderdetail;
    }
}


// ==========================
// 订单详情类 OrderDetail
// ==========================
public class OrderDetail {

    // 属性
    private int quantity;

    // 多重性：OrderDetail --> 1 Order
    private Order order;
    // 多重性：OrderDetail --> 1 Product
    private Product product;

    // 构造器，确保 OrderDetail 创建时满足以上约束
    public OrderDetail(Order order, Product product) {
        if (order == null)
            throw new IllegalArgumentException("OrderDetail 必须属于一个 Order");
        if (product == null)
            throw new IllegalArgumentException("OrderDetail 必须关联一个 Product");

        this.order = order;
        this.product = product;
    }

    // 计算小计
    public float calculateSubTotal() {
        return product.getPriceForQuantity() * quantity;
    }

    // 计算总重量
    public float calculateWeight() {
        return product.getWeight() * quantity;
    }
}


// ==========================
// 商品类 Product
// ==========================
public class Product {

    // 属性
    private String title;
    private float weight;
    private String description;
    // 必须添加题干中没有的额外属性 - 单价，否则 getPriceForQuantity() 方法难以实现
    private float unitPrice;

    // 多重性：Product --> 0..* OrderDetail
    private List<OrderDetail> orderDetails;

    // 计算价格
    public float getPriceForQuantity() {
        return unitPrice * weight;
    }

    // 返回重量
    public float getWeight() {
        return weight;
    }
}


// ==========================
// 支付类 Payment
// ==========================
public class Payment {

    // 属性
    protected float amount;

    // 多重性：Payment --> 1..* Order
    protected List<Order> orders;

    // 构造器，确保 Payment 创建时满足以上约束
    public Payment(List<Order> orders) {
        if (orders == null || orders.isEmpty())
            throw new IllegalArgumentException("Payment 必须至少关联一个 Order");

        this.orders = orders;
    }
}


// ==========================
// 信用卡类 Credit
// ==========================
public class Credit extends Payment {

    // 属性
    private String number;
    private String type;
    private LocalDate expireDate;

    // 构造器，需要调用父类 Payment 的构造器，满足其约束
    public Credit(List<Order> orders) {
        super(orders);
    }
}


// ==========================
// 现金类 Cash
// ==========================
public class Cash extends Payment {

    // 属性
    private float cashTendered;

    // 构造器，需要调用父类 Payment 的构造器，满足其约束
    public Cash(List<Order> orders) {
        super(orders);
    }
}


// ==========================
// 转账类 WireTransfer
// ==========================
public class WireTransfer extends Payment {

    // 属性
    private String bankID;
    private String bankName;

    // 构造器，需要调用父类 Payment 的构造器，满足其约束
    public WireTransfer(List<Order> orders) {
        super(orders);
    }
}


// ==========================
// 支付宝类 AliPay
// ==========================
public class AliPay extends Payment {

    // 属性
    private String number;

    // 构造器，需要调用父类 Payment 的构造器，满足其约束
    public AliPay(List<Order> orders) {
        super(orders);
    }
}


// ==========================
// 微信支付类 WeixinPay
// ==========================
public class WeixinPay extends Payment {

    // 属性
    private String number;

    // 构造器，需要调用父类 Payment 的构造器，满足其约束
    public WeixinPay(List<Order> orders) {
        super(orders);
    }
}
