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

    @JoinColumn(name = "PROFILE_ID")
    private Long profile;

    @JoinColumn(name = "SUBSCRIBER_ID")
    private Long subscriber;
}
