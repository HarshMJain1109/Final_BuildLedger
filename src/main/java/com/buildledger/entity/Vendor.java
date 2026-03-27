package com.buildledger.entity;

import com.buildledger.enums.VendorStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vendors")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

//    @NotBlank(message = "Password is required")
//    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    ////    @Pattern(
    ////            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
    ////            message = "Password must contain uppercase, lowercase, number and special character"
    ////    )
//    private String password;

    @Column(name = "contact_info", columnDefinition = "TEXT")
    private String contactInfo;

    @Column(name = "email", unique = true, length = 100)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private VendorStatus status = VendorStatus.PENDING;

    /**
     * The ID of the {@link User} entry created when this vendor was approved.
     *
     * <p><b>Design rule:</b> Once set, all operational actions that require
     * identifying the vendor as a system principal (login, invoice submission,
     * etc.) MUST use this {@code userId}, NOT the {@code vendorId}.
     * {@code vendorId} is only for vendor-management operations (admin, docs).
     *
     * <p>A {@code null} value means the vendor has NOT yet been approved and
     * has no user account. Using this as a guard prevents duplicate user creation
     * even if {@code autoUpdateVendorStatus} is called multiple times.
     *
     * <p>DB migration: {@code ALTER TABLE vendors ADD COLUMN user_id BIGINT NULL UNIQUE;}
     */
    @Column(name = "user_id", unique = true)
    private Long userId;

    /**
     * Optimistic locking to prevent concurrent approval race conditions
     * (two admins approving the same vendor simultaneously).
     */
    @Version
    @Column(name = "version")
    private Long version;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<VendorDocument> documents;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contract> contracts;
}
