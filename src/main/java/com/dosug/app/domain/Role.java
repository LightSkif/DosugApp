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
public class Role {

    private static final String ADMIN_ROLE_NAME = "ADMIN";

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "role")
    private String roleName;

    public Role() {
    }

    /**
     * Возвращает роль идентичную админу
     */
    public static Role admin() {
        Role role = new Role();
        // так как равенство роли мы проверяем только по названию
        // то id проставлять смысла нет
        role.setRoleName(ADMIN_ROLE_NAME);
        return role;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return roleName != null ? roleName.equals(role.roleName) : role.roleName == null;
    }

    @Override
    public int hashCode() {
        return roleName != null ? roleName.hashCode() : 0;
    }
}
