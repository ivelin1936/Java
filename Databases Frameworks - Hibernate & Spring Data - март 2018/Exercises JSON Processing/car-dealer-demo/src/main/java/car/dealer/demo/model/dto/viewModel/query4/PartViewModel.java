package car.dealer.demo.model.dto.viewModel.query4;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class PartViewModel {

    @Expose
    @SerializedName(value = "Name")
    private String name;

    @Expose
    @SerializedName(value = "Price")
    private BigDecimal price;

    public PartViewModel() {
    }

    public PartViewModel(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
