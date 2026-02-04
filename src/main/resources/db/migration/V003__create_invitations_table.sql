CREATE TABLE invitations
(
    id         BIGSERIAL PRIMARY KEY,
    group_id   BIGINT      NOT NULL REFERENCES groups (id) ON DELETE CASCADE,
    inviter_id BIGINT      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    token      VARCHAR     NOT NULL UNIQUE,
    status     VARCHAR(20) NOT NULL DEFAULT 'pending',
    created_at TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP   NOT NULL DEFAULT NOW(),
    expires_at TIMESTAMP,

    CONSTRAINT chk_invitation_status
        CHECK (status IN ('pending', 'accepted', 'expired'))
);
