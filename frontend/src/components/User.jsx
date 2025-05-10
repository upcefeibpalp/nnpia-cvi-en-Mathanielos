import React, { useState } from 'react';

export default function User({
                                 id,
                                 email,
                                 password,
                                 version,
                                 profile,
                                 initialActive = false
                             }) {
    const [active, setActive] = useState(initialActive);

    return (
        <div className="user-card" style={{border: '1px solid #ccc', padding: '1rem', margin: '1rem 0'}}>
            <p><strong>ID:</strong> {id}</p>
            <p><strong>Email:</strong> {email}</p>
            <p><strong>Password:</strong> {password}</p>
            <p><strong>Version:</strong> {version}</p>

            {profile && (
                <>
                    <p><strong>Profile ID:</strong> {profile.id}</p>
                    <p><strong>Full Name:</strong> {profile.fullName}</p>
                    <p><strong>Bio:</strong> {profile.bio}</p>
                </>
            )}

            <p>
                <strong>Active:</strong> {active ? 'Yes' : 'No'}
            </p>
            <button onClick={() => setActive(!active)}>
                {active ? 'Deactivate' : 'Activate'}
            </button>
        </div>
    );
}
