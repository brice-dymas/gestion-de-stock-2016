/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.persistence.service;

import com.cami.persistence.IOperations;
import com.cami.persistence.model.TypeOperation;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
public interface ITypeOperationService extends IOperations<TypeOperation>
{

    public TypeOperation findByIntitule(String nomType);
}
