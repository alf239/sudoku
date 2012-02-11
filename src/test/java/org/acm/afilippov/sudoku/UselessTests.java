package org.acm.afilippov.sudoku;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Tests that make no particular sense but added here for the sake of coverage
 */
public class UselessTests {
    private SecurityManager securityManager;

    @Before
    public void checkpoint() {
        securityManager = System.getSecurityManager();
    }

    @After
    public void rollback() {
        System.setSecurityManager(securityManager);
    }

    @Test
    public void coverConstructorOfMain() {
        new Main();
    }

    @Test(expected = SecurityException.class)
    public void helpMessage() throws IOException {
        final SecurityManager securityManager = new SecurityManager() {
            public void checkPermission(java.security.Permission permission) {
                if (permission.getName().contains("exitVM")) {
                    throw new SecurityException("System.exit calls not allowed!");
                }
            }
        };
        System.setSecurityManager(securityManager);

        Main.main(new String[0]);
    }

    @Test
    public void properExecution() throws IOException {
        Main.main(new String[]{
                UselessTests.class.getResource("/tasks.txt").getPath(),
                "classic"
        });
    }
}
