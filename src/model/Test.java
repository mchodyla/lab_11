package model;

import java.math.BigDecimal;

public class Test
{
    private BigDecimal _K1;
    public BigDecimal getK1()
    {
        return this._K1;
    }
    public void setK1(BigDecimal value)
    {
        this._K1 = value;
    }

    private String _K2;
    public String getK2()
    {
        return this._K2;
    }
    public void setK2(String value)
    {
        this._K2 = value;
    }

    private String _K3;
    public String getK3()
    {
        return this._K3;
    }
    public void setK3(String value)
    {
        this._K3 = value;
    }

    public Test(BigDecimal K1_,String K2_,String K3_)
    {
        this._K1 = K1_;
        this._K2 = K2_;
        this._K3 = K3_;
    }
}
