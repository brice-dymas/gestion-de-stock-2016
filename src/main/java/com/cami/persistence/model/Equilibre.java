/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.model;

import javax.validation.constraints.Digits;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public class Equilibre
{

    @NumberFormat
    @Digits(fraction = 0, integer = Integer.MAX_VALUE, message = "{digit.message}")
    private int quantite;

    public Equilibre()
    {
    }

    public void setQuantite(int quantite)
    {
        this.quantite = quantite;
    }

    public int getQuantite()
    {
        return quantite;
    }

}
