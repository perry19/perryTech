/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.perryTech.Repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.example.perryTech.Model.Contact;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author compilerarmelovic
 */
public interface ContactRepository extends PagingAndSortingRepository<Contact, Long>, 
        JpaSpecificationExecutor<Contact> {
}
