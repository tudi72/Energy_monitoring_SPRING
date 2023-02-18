package ro.tuc.ds2020.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "maxHourlyEnergyCons",nullable = false)
    private Double maxHourlyEnergyCons;

    @OneToMany(mappedBy = "device",fetch = FetchType.EAGER)
    private Collection<EnergyConsumption> consumptions = new ArrayList<>();
}
