package com.projectD.fakedata.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bdo_id", nullable = false, unique = true)
    private Long bdoId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "client")
    private Set<ManagedObject> objects = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBdoId() {
        return bdoId;
    }

    public void setBdoId(Long bdoId) {
        this.bdoId = bdoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ManagedObject> getObjects() {
        return objects;
    }

    public void setObjects(Set<ManagedObject> objects) {
        this.objects = objects;
    }
}
