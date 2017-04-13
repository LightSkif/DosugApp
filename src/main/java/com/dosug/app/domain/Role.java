package com.dosug.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author radmirnovii
 */
@Entity
@Table(name = "roles")
public class  Role {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    private String roleName;

    public Role() {
    }

    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
