/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.dtt.repositories;

import com.dtt.pojo.MissingReport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MR TU
 */
@Repository
public interface MissingReportRepository {
    MissingReport addOrUpdateMissingReport(MissingReport mr);
}
