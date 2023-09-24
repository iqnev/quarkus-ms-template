package org.iqnev.boilerplate.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cities")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(name = "public_id")
  private String publicId;

  @OneToOne private StateEntity state;

  @Column(nullable = false, name = "created_at")
  private Instant createdAt;

  @Column(nullable = false, name = "last_modified")
  private Instant lastModified;

  @PrePersist
  protected void onCreate() {
    createdAt = Instant.now();
    lastModified = createdAt;
  }

  @PreUpdate
  protected void onUpdate() {
    lastModified = Instant.now();
  }
}
