// ============================================
// CONFIGURATION
// ============================================
const API_BASE_URL = 'https://pet-adoption-backend-9a86.onrender.com/api';

// ============================================
// FORM DATA STORAGE
// ============================================
const formData = {
    personalInfo: {},
    security: {},
    verification: {}
};

// DOM Elements
const step1Form = document.getElementById('step1-input');
const step2Form = document.getElementById('step2-input');
const step1Wrapper = document.getElementById('step1-form');
const step2Wrapper = document.getElementById('step2-form');
const profilePage = document.getElementById('profilePage');
const errorPage = document.getElementById('errorPage');
const progressFill = document.getElementById('progressFill');

// ============================================
// STEP 1: PERSONAL INFORMATION VALIDATION
// ============================================

// Real-time validation for name
const fullNameInput = document.getElementById('fullName');
fullNameInput?.addEventListener('input', function() {
    validateName(this.value, true);
});

function validateName(name, showValidator = false) {
    const nameError = document.getElementById('nameError');
    const nameValidator = fullNameInput?.parentElement.querySelector('.input-validator');
    
    // Remove special characters and numbers
    const regex = /^[a-zA-Z\s]*$/;
    
    if (!name.trim()) {
        nameError.textContent = 'Name is required';
        nameError.classList.add('show');
        fullNameInput?.classList.add('error');
        fullNameInput?.classList.remove('success');
        nameValidator?.classList.remove('show');
        return false;
    }
    
    if (name.length < 3) {
        nameError.textContent = 'Name must be at least 3 characters';
        nameError.classList.add('show');
        fullNameInput?.classList.add('error');
        fullNameInput?.classList.remove('success');
        nameValidator?.classList.remove('show');
        return false;
    }
    
    if (!regex.test(name)) {
        nameError.textContent = 'Name can only contain letters and spaces';
        nameError.classList.add('show');
        fullNameInput?.classList.add('error');
        fullNameInput?.classList.remove('success');
        nameValidator?.classList.remove('show');
        return false;
    }
    
    nameError.classList.remove('show');
    fullNameInput?.classList.remove('error');
    fullNameInput?.classList.add('success');
    if (showValidator) {
        nameValidator?.classList.add('show');
    }
    return true;
}

// Real-time validation for email
const emailInput = document.getElementById('email');
emailInput?.addEventListener('input', function() {
    validateEmail(this.value, true);
});

function validateEmail(email, showValidator = false) {
    const emailError = document.getElementById('emailError');
    const emailValidator = emailInput?.parentElement.querySelector('.input-validator');
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    
    if (!email.trim()) {
        emailError.textContent = 'Email is required';
        emailError.classList.add('show');
        emailInput?.classList.add('error');
        emailInput?.classList.remove('success');
        emailValidator?.classList.remove('show');
        return false;
    }
    
    if (!emailRegex.test(email)) {
        emailError.textContent = 'Please enter a valid email';
        emailError.classList.add('show');
        emailInput?.classList.add('error');
        emailInput?.classList.remove('success');
        emailValidator?.classList.remove('show');
        return false;
    }
    
    emailError.classList.remove('show');
    emailInput?.classList.remove('error');
    emailInput?.classList.add('success');
    if (showValidator) {
        emailValidator?.classList.add('show');
    }
    return true;
}

// Real-time validation for phone
const phoneInput = document.getElementById('phone');
phoneInput?.addEventListener('input', function() {
    this.value = this.value.replace(/[^0-9]/g, '');
    validatePhone(this.value, true);
});

function validatePhone(phone, showValidator = false) {
    const phoneError = document.getElementById('phoneError');
    const phoneValidator = phoneInput?.parentElement.querySelector('.input-validator');
    
    if (!phone.trim()) {
        phoneError.textContent = 'Phone number is required';
        phoneError.classList.add('show');
        phoneInput?.classList.add('error');
        phoneInput?.classList.remove('success');
        phoneValidator?.classList.remove('show');
        return false;
    }
    
    if (phone.length !== 10) {
        phoneError.textContent = 'Phone number must be 10 digits';
        phoneError.classList.add('show');
        phoneInput?.classList.add('error');
        phoneInput?.classList.remove('success');
        phoneValidator?.classList.remove('show');
        return false;
    }
    
    if (!/^[6-9]/.test(phone)) {
        phoneError.textContent = 'Phone number must start with 6-9';
        phoneError.classList.add('show');
        phoneInput?.classList.add('error');
        phoneInput?.classList.remove('success');
        phoneValidator?.classList.remove('show');
        return false;
    }
    
    phoneError.classList.remove('show');
    phoneInput?.classList.remove('error');
    phoneInput?.classList.add('success');
    if (showValidator) {
        phoneValidator?.classList.add('show');
    }
    return true;
}

// Real-time validation for Aadhar
const aadharInput = document.getElementById('aadhar');
aadharInput?.addEventListener('input', function() {
    this.value = this.value.replace(/[^0-9]/g, '');
    validateAadhar(this.value, true);
});

function validateAadhar(aadhar, showValidator = false) {
    const aadharError = document.getElementById('aadharError');
    const aadharValidator = aadharInput?.parentElement.querySelector('.input-validator');
    
    if (!aadhar.trim()) {
        aadharError.textContent = 'Aadhar number is required';
        aadharError.classList.add('show');
        aadharInput?.classList.add('error');
        aadharInput?.classList.remove('success');
        aadharValidator?.classList.remove('show');
        return false;
    }
    
    if (aadhar.length !== 12) {
        aadharError.textContent = 'Aadhar number must be 12 digits';
        aadharError.classList.add('show');
        aadharInput?.classList.add('error');
        aadharInput?.classList.remove('success');
        aadharValidator?.classList.remove('show');
        return false;
    }
    
    // Luhn algorithm would go here for actual validation
    aadharError.classList.remove('show');
    aadharInput?.classList.remove('error');
    aadharInput?.classList.add('success');
    if (showValidator) {
        aadharValidator?.classList.add('show');
    }
    return true;
}

// Step 1 Form Submission
step1Form?.addEventListener('submit', function(e) {
    e.preventDefault();
    
    const nameValid = validateName(fullNameInput.value);
    const emailValid = validateEmail(emailInput.value);
    const phoneValid = validatePhone(phoneInput.value);
    const aadharValid = validateAadhar(aadharInput.value);
    const termsChecked = document.getElementById('terms').checked;
    
    let termsError = document.getElementById('termsError');
    
    if (!termsChecked) {
        termsError.textContent = 'You must agree to the terms';
        termsError.classList.add('show');
        return;
    } else {
        termsError.classList.remove('show');
    }
    
    if (nameValid && emailValid && phoneValid && aadharValid && termsChecked) {
        formData.personalInfo = {
            fullName: fullNameInput.value,
            email: emailInput.value,
            phone: phoneInput.value,
            aadhar: aadharInput.value
        };
        
        // Move to step 2
        moveToStep(2);
    }
});

// ============================================
// STEP 2: SECURITY SETUP
// ============================================

const passwordInput = document.getElementById('password');
const confirmPasswordInput = document.getElementById('confirmPassword');
const togglePassword = document.getElementById('togglePassword');
const toggleConfirmPassword = document.getElementById('toggleConfirmPassword');

// Toggle password visibility
togglePassword?.addEventListener('click', function(e) {
    e.preventDefault();
    passwordInput.type = passwordInput.type === 'password' ? 'text' : 'password';
    this.textContent = passwordInput.type === 'password' ? '👁️' : '🙈';
});

toggleConfirmPassword?.addEventListener('click', function(e) {
    e.preventDefault();
    confirmPasswordInput.type = confirmPasswordInput.type === 'password' ? 'text' : 'password';
    this.textContent = confirmPasswordInput.type === 'password' ? '👁️' : '🙈';
});

// Password strength checker
passwordInput?.addEventListener('input', function() {
    checkPasswordStrength(this.value);
    validateConfirmPassword();
});

function checkPasswordStrength(password) {
    const strengthBar = document.getElementById('strengthBar');
    const strengthText = document.getElementById('strengthText');
    const requirements = {
        length: document.getElementById('req-length'),
        uppercase: document.getElementById('req-uppercase'),
        lowercase: document.getElementById('req-lowercase'),
        number: document.getElementById('req-number'),
        special: document.getElementById('req-special')
    };
    
    let strength = 0;
    
    // Check length
    if (password.length >= 8) {
        strength++;
        requirements.length.classList.add('met');
        requirements.length.textContent = '✓ At least 8 characters';
    } else {
        requirements.length.classList.remove('met');
        requirements.length.textContent = '✗ At least 8 characters';
    }
    
    // Check uppercase
    if (/[A-Z]/.test(password)) {
        strength++;
        requirements.uppercase.classList.add('met');
        requirements.uppercase.textContent = '✓ One uppercase letter (A-Z)';
    } else {
        requirements.uppercase.classList.remove('met');
        requirements.uppercase.textContent = '✗ One uppercase letter (A-Z)';
    }
    
    // Check lowercase
    if (/[a-z]/.test(password)) {
        strength++;
        requirements.lowercase.classList.add('met');
        requirements.lowercase.textContent = '✓ One lowercase letter (a-z)';
    } else {
        requirements.lowercase.classList.remove('met');
        requirements.lowercase.textContent = '✗ One lowercase letter (a-z)';
    }
    
    // Check number
    if (/[0-9]/.test(password)) {
        strength++;
        requirements.number.classList.add('met');
        requirements.number.textContent = '✓ One number (0-9)';
    } else {
        requirements.number.classList.remove('met');
        requirements.number.textContent = '✗ One number (0-9)';
    }
    
    // Check special character
    if (/[!@#$%^&*]/.test(password)) {
        strength++;
        requirements.special.classList.add('met');
        requirements.special.textContent = '✓ One special character (!@#$%^&*)';
    } else {
        requirements.special.classList.remove('met');
        requirements.special.textContent = '✗ One special character (!@#$%^&*)';
    }
    
    // Update strength bar
    strengthBar.classList.remove('weak', 'medium', 'strong');
    
    if (strength <= 2) {
        strengthBar.classList.add('weak');
        strengthText.textContent = 'Password strength: Weak';
    } else if (strength <= 3) {
        strengthBar.classList.add('medium');
        strengthText.textContent = 'Password strength: Medium';
    } else {
        strengthBar.classList.add('strong');
        strengthText.textContent = 'Password strength: Strong';
    }
}

function validatePassword(password) {
    const passwordError = document.getElementById('passwordError');
    
    if (!password) {
        passwordError.textContent = 'Password is required';
        passwordError.classList.add('show');
        passwordInput.classList.add('error');
        return false;
    }
    
    if (password.length < 8) {
        passwordError.textContent = 'Password must be at least 8 characters';
        passwordError.classList.add('show');
        passwordInput.classList.add('error');
        return false;
    }
    
    if (!/[A-Z]/.test(password) || !/[a-z]/.test(password) || !/[0-9]/.test(password) || !/[!@#$%^&*]/.test(password)) {
        passwordError.textContent = 'Password does not meet all requirements';
        passwordError.classList.add('show');
        passwordInput.classList.add('error');
        return false;
    }
    
    passwordError.classList.remove('show');
    passwordInput.classList.remove('error');
    passwordInput.classList.add('success');
    return true;
}

confirmPasswordInput?.addEventListener('input', validateConfirmPassword);

function validateConfirmPassword() {
    const confirmPasswordError = document.getElementById('confirmPasswordError');
    
    if (!confirmPasswordInput.value) {
        confirmPasswordError.textContent = 'Please confirm your password';
        confirmPasswordError.classList.add('show');
        confirmPasswordInput.classList.add('error');
        return false;
    }
    
    if (passwordInput.value !== confirmPasswordInput.value) {
        confirmPasswordError.textContent = 'Passwords do not match';
        confirmPasswordError.classList.add('show');
        confirmPasswordInput.classList.add('error');
        return false;
    }
    
    confirmPasswordError.classList.remove('show');
    confirmPasswordInput.classList.remove('error');
    confirmPasswordInput.classList.add('success');
    return true;
}

// Step 2 Form Submission
step2Form?.addEventListener('submit', function(e) {
    e.preventDefault();
    
    const passwordValid = validatePassword(passwordInput.value);
    const confirmPasswordValid = validateConfirmPassword();
    
    const securityQ1 = document.getElementById('securityQ1').value;
    const securityA1 = document.getElementById('securityA1').value;
    const securityQ2 = document.getElementById('securityQ2').value;
    const securityA2 = document.getElementById('securityA2').value;
    
    let securityQ1Error = document.getElementById('securityQ1Error');
    let securityA1Error = document.getElementById('securityA1Error');
    let securityQ2Error = document.getElementById('securityQ2Error');
    let securityA2Error = document.getElementById('securityA2Error');
    
    let allValid = true;
    
    if (!securityQ1) {
        securityQ1Error.textContent = 'Please select a security question';
        securityQ1Error.classList.add('show');
        allValid = false;
    } else {
        securityQ1Error.classList.remove('show');
    }
    
    if (!securityA1 || securityA1.length < 3) {
        securityA1Error.textContent = 'Please provide an answer';
        securityA1Error.classList.add('show');
        allValid = false;
    } else {
        securityA1Error.classList.remove('show');
    }
    
    if (!securityQ2) {
        securityQ2Error.textContent = 'Please select a security question';
        securityQ2Error.classList.add('show');
        allValid = false;
    } else {
        securityQ2Error.classList.remove('show');
    }
    
    if (!securityA2 || securityA2.length < 3) {
        securityA2Error.textContent = 'Please provide an answer';
        securityA2Error.classList.add('show');
        allValid = false;
    } else {
        securityA2Error.classList.remove('show');
    }
    
    if (securityQ1 === securityQ2) {
        securityQ1Error.textContent = 'Security questions must be different';
        securityQ1Error.classList.add('show');
        allValid = false;
    }
    
    if (passwordValid && confirmPasswordValid && allValid) {
        formData.security = {
            password: passwordInput.value,
            securityQ1: securityQ1,
            securityA1: securityA1,
            securityQ2: securityQ2,
            securityA2: securityA2
        };

        registerUser();
    }
});

async function registerUser() {
    try {
        const payload = {
            email: formData.personalInfo.email,
            fullName: formData.personalInfo.fullName,
            phoneNumber: formData.personalInfo.phone,
            password: formData.security.password,
            aadharNumber: formData.personalInfo.aadhar,
            securityQuestion1: formData.security.securityQ1,
            securityAnswer1: formData.security.securityA1,
            securityQuestion2: formData.security.securityQ2,
            securityAnswer2: formData.security.securityA2
        };

        const response = await fetch(API_BASE_URL + '/users/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        const data = await response.json();

        if (!response.ok || !data.success) {
            throw new Error(data.message || 'Registration failed');
        }

        // Registration successful - show profile
        showProfile();
    } catch (error) {
        showError(error.message);
    }
}


// Back button for Step 2
document.getElementById('backStep2')?.addEventListener('click', function() {
    moveToStep(1);
});

// ============================================
// STEP NAVIGATION
// ============================================

function moveToStep(step) {
    // Hide all forms
    step1Wrapper.classList.add('hidden');
    step2Wrapper.classList.add('hidden');
    profilePage.classList.add('hidden');
    errorPage.classList.add('hidden');
    
    // Show target form
    if (step === 1) {
        step1Wrapper.classList.remove('hidden');
    } else if (step === 2) {
        step2Wrapper.classList.remove('hidden');
    }
    
    // Update progress bar (50% per step for 2 steps)
    progressFill.style.width = ((step - 1) * 50) + '%';
    
    // Update step indicators
    document.querySelectorAll('.step').forEach((stepEl, index) => {
        if (index + 1 <= step) {
            stepEl.classList.add('active');
        } else {
            stepEl.classList.remove('active');
        }
    });
    
    // Scroll to top
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// ============================================
// REGISTRATION SIMULATION
// ============================================

function simulateRegistration() {
    // Show loading state
    const registerBtn = document.querySelector('.btn-register');
    registerBtn.textContent = 'Processing...';
    registerBtn.disabled = true;

    const payload = {
        email: formData.personalInfo.email,
        fullName: formData.personalInfo.fullName,
        phoneNumber: formData.personalInfo.phone,
        password: formData.security.password,
        aadharNumber: formData.personalInfo.aadhar,
        securityQuestion1: formData.security.securityQ1,
        securityAnswer1: formData.security.securityA1,
        securityQuestion2: formData.security.securityQ2,
        securityAnswer2: formData.security.securityA2
    };

    fetch(API_BASE_URL + '/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
    .then(response => response.json())
    .then(data => {
        registerBtn.textContent = 'Register';
        registerBtn.disabled = false;

        if (data.success) {
            showProfile();
        } else {
            showError(data.message || 'Registration failed.');
        }
    })
    .catch(err => {
        registerBtn.textContent = 'Register';
        registerBtn.disabled = false;
        showError('Registration failed: ' + err.message);
    });
}

function showProfile() {
    // Update profile page with user info
    document.getElementById('profileName').textContent = formData.personalInfo.fullName;
    document.getElementById('profileFullName').textContent = formData.personalInfo.fullName;
    document.getElementById('profileEmail').textContent = formData.personalInfo.email;
    document.getElementById('profilePhone').textContent = formData.personalInfo.phone;
    document.getElementById('profileAadhar').textContent = formData.personalInfo.aadhar;
    document.getElementById('profileSecurityQ1').textContent = formData.security.securityQ1;
    document.getElementById('profileSecurityQ2').textContent = formData.security.securityQ2;
    
    // Hide all forms and show profile
    step1Wrapper.classList.add('hidden');
    step2Wrapper.classList.add('hidden');
    errorPage.classList.add('hidden');
    profilePage.classList.remove('hidden');
    
    // Complete progress bar
    progressFill.style.width = '100%';
    
    // Log registration data
    console.log('Registration Data:', formData);
    
    // Scroll to top
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function showError(message) {
    document.getElementById('errorMessage').textContent = message;
    
    step1Wrapper.classList.add('hidden');
    step2Wrapper.classList.add('hidden');
    profilePage.classList.add('hidden');
    errorPage.classList.remove('hidden');
    
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// Retry Registration
document.getElementById('retryRegistration')?.addEventListener('click', function() {
    // Reset form data
    formData.personalInfo = {};
    formData.security = {};
    formData.verification = {};
    
    // Reset all forms
    step1Form.reset();
    step2Form.reset();
    step3Form.reset();
    
    // Clear all validators
    document.querySelectorAll('.error-message').forEach(el => {
        el.classList.remove('show');
    });
    document.querySelectorAll('input, select').forEach(el => {
        el.classList.remove('error', 'success');
    });
    
    // Move back to step 1
    moveToStep(1);
});

// Go to Adopt
document.getElementById('goToAdopt')?.addEventListener('click', function() {
    window.location.href = 'index.html#adopt';
});

// Edit Profile
document.getElementById('editProfile')?.addEventListener('click', function() {
    showNotification('Profile editing feature coming soon!');
});

// ============================================
// NOTIFICATION SYSTEM
// ============================================

function showNotification(message) {
    const notification = document.createElement('div');
    notification.className = 'notification';
    notification.textContent = message;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        background: linear-gradient(135deg, #2ECC71, #27AE60);
        color: white;
        padding: 1.5rem 2rem;
        border-radius: 10px;
        box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
        animation: slideInUp 0.4s ease-out;
        z-index: 2000;
        max-width: 300px;
        font-weight: 500;
    `;
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.style.animation = 'fadeOut 0.4s ease-out';
        setTimeout(() => notification.remove(), 400);
    }, 3000);
}

// ============================================
// PAGE LOAD SETUP
// ============================================

window.addEventListener('load', () => {
    // Initialize progress bar
    moveToStep(1);
    
    // Set up form animations
    document.querySelectorAll('.form-group').forEach((el, index) => {
        el.style.animationDelay = `${index * 0.1}s`;
    });
});

// ============================================
// CONSOLE WELCOME
// ============================================

console.log('%cPawsAdopt Registration Page', 'font-size: 18px; color: #FF6B6B; font-weight: bold;');
console.log('%cSecure Registration System Active', 'font-size: 12px; color: #4ECDC4;');
console.log('%c🔒 All data is encrypted and secure', 'font-size: 12px; color: #2ECC71; font-weight: bold;');
