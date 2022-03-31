package ru.vsu.twitter.entity;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "subscribes")
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "PROFILE_ID")
    private User profile;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "SUBSCRIBER_ID")
    private User subscriber;
}
