// ============================================
// PETS DATA
// ============================================
const petsData = {
    all: ['dog', 'cat', 'rabbit', 'bird'],
    dog: ['dog'],
    cat: ['cat'],
    rabbit: ['rabbit'],
    bird: ['bird']
};

// ============================================
// FILTER FUNCTIONALITY
// ============================================
const filterButtons = document.querySelectorAll('.filter-btn');
const petCards = document.querySelectorAll('.pet-card');

filterButtons.forEach(button => {
    button.addEventListener('click', () => {
        // Update active button
        filterButtons.forEach(btn => btn.classList.remove('active'));
        button.classList.add('active');

        // Get filter value
        const filterValue = button.dataset.filter;
        const petsToShow = petsData[filterValue];

        // Animate pet cards
        petCards.forEach(card => {
            const petType = card.dataset.pet;
            
            if (petsToShow.includes(petType)) {
                card.style.animation = 'none';
                setTimeout(() => {
                    card.style.animation = 'slideInUp 0.6s ease-out';
                    card.style.display = 'block';
                }, 10);
            } else {
                card.style.display = 'none';
            }
        });
    });
});

// ============================================
// SMOOTH SCROLL FOR NAV LINKS
// ============================================
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});

// ============================================
// CTA BUTTON CLICK
// ============================================
const ctaButton = document.querySelector('.cta-button');
ctaButton.addEventListener('click', () => {
    document.querySelector('#adopt').scrollIntoView({
        behavior: 'smooth',
        block: 'start'
    });
});

// ============================================
// ADOPT BUTTON INTERACTIONS
// ============================================
const adoptButtons = document.querySelectorAll('.adopt-btn');

adoptButtons.forEach(button => {
    button.addEventListener('click', (e) => {
        e.stopPropagation();
        
        // Get pet name
        const petName = button.parentElement.querySelector('.pet-name').textContent;
        
        // Create notification
        showNotification(`Thanks for your interest in ${petName}! Contact us to begin the adoption process.`);
        
        // Add visual feedback
        button.textContent = '❤️ Adoption Requested!';
        button.style.background = 'linear-gradient(135deg, #2ECC71, #27AE60)';
        
        setTimeout(() => {
            button.textContent = `Adopt ${petName}`;
            button.style.background = '';
        }, 2000);
    });
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
    
    // Remove after 3 seconds
    setTimeout(() => {
        notification.style.animation = 'fadeOut 0.4s ease-out';
        setTimeout(() => notification.remove(), 400);
    }, 3000);
}

// ============================================
// FORM SUBMISSION
// ============================================
const contactForm = document.querySelector('.contact-form');

if (contactForm) {
    contactForm.addEventListener('submit', (e) => {
        e.preventDefault();
        
        // Create success notification
        showNotification('Message sent successfully! We\'ll get back to you soon.');
        
        // Clear form
        contactForm.reset();
        
        // Change button for visual feedback
        const submitBtn = contactForm.querySelector('.submit-btn');
        const originalText = submitBtn.textContent;
        submitBtn.textContent = '✅ Message Sent!';
        submitBtn.style.background = 'linear-gradient(135deg, #2ECC71, #27AE60)';
        
        setTimeout(() => {
            submitBtn.textContent = originalText;
            submitBtn.style.background = '';
        }, 2000);
    });
}

// ============================================
// CUSTOM PET UPLOAD FORM
// ============================================
const customPetForm = document.querySelector('#custom-pet-form');

if (customPetForm) {
    // Add file size display when file is selected
    const fileInput = document.querySelector('#customPetPhoto');
    const fileSizeDisplay = document.createElement('small');
    fileSizeDisplay.id = 'fileSizeDisplay';
    fileSizeDisplay.style.cssText = 'grid-column: span 2; color: var(--text-light); font-size: 0.85rem; margin-top: -0.5rem; margin-bottom: 0.5rem;';
    fileInput.parentNode.insertBefore(fileSizeDisplay, fileInput.nextSibling);

    fileInput.addEventListener('change', (e) => {
        const file = e.target.files[0];
        if (file) {
            const sizeMB = (file.size / (1024 * 1024)).toFixed(2);
            fileSizeDisplay.textContent = `Selected: ${file.name} (${sizeMB} MB)`;
            fileSizeDisplay.style.color = file.size > 5 * 1024 * 1024 ? '#e74c3c' : 'var(--text-light)';
        } else {
            fileSizeDisplay.textContent = '';
        }
    });

    customPetForm.addEventListener('submit', (e) => {
        e.preventDefault();

        const name = document.querySelector('#customPetName').value.trim();
        const breed = document.querySelector('#customPetBreed').value.trim();
        const type = document.querySelector('#customPetType').value;
        const file = document.querySelector('#customPetPhoto').files[0];

        if (!name || !breed || !type || !file) {
            showNotification('Please complete the form and select an image.');
            return;
        }

        // Validate file size (max 5MB)
        const maxSize = 5 * 1024 * 1024; // 5MB in bytes
        if (file.size > maxSize) {
            showNotification('File size too large. Please select an image under 5MB.');
            return;
        }

        // Validate file type
        const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp'];
        if (!allowedTypes.includes(file.type)) {
            showNotification('Invalid file type. Please select a JPG, PNG, GIF, or WebP image.');
            return;
        }

        const reader = new FileReader();
        reader.onload = function(event) {
            const imageUrl = event.target.result;
            const petsContainer = document.querySelector('.pets-container');
            const card = document.createElement('div');
            card.className = 'pet-card';
            card.dataset.pet = type;
            card.innerHTML = `
                <div class="pet-image-wrapper">
                    <img class="pet-image" src="${imageUrl}" alt="${name}">
                    <div class="pet-badge">${type.charAt(0).toUpperCase() + type.slice(1)}</div>
                </div>
                <div class="pet-info">
                    <h3 class="pet-name">${name}</h3>
                    <p class="pet-breed">${breed}</p>
                    <p class="pet-description">User submitted custom pet photo</p>
                    <div class="pet-details">
                        <span class="detail-item">Custom</span>
                        <span class="detail-item">Unknown</span>
                    </div>
                    <button class="adopt-btn">Adopt ${name}</button>
                </div>
            `;

            petsContainer.appendChild(card);
            showNotification(`${name} has been added to the pet list!`);

            customPetForm.reset();
            document.querySelector('#fileSizeDisplay').textContent = '';

            const adoptBtn = card.querySelector('.adopt-btn');
            adoptBtn.addEventListener('click', (clickEvent) => {
                clickEvent.stopPropagation();
                showNotification(`Thanks for your interest in ${name}! Contact us to begin the adoption process.`);
                adoptBtn.textContent = '❤️ Adoption Requested!';
                adoptBtn.style.background = 'linear-gradient(135deg, #2ECC71, #27AE60)';
                setTimeout(() => {
                    adoptBtn.textContent = `Adopt ${name}`;
                    adoptBtn.style.background = '';
                }, 2000);
            });
        };

        reader.readAsDataURL(file);
    });
}

// ============================================
// SCROLL ANIMATIONS
// ============================================
const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -100px 0px'
};

const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
        }
    });
}, observerOptions);

// Observe all pet cards and benefit cards
document.querySelectorAll('.pet-card, .benefit-card').forEach(card => {
    card.style.opacity = '0';
    card.style.transform = 'translateY(30px)';
    card.style.transition = 'all 0.6s ease-out';
    observer.observe(card);
});

// ============================================
// PARALLAX EFFECT ON SCROLL
// ============================================
window.addEventListener('scroll', () => {
    const scrollPosition = window.pageYOffset;
    const heroAnimation = document.querySelector('.hero-animation');
    
    if (heroAnimation) {
        heroAnimation.style.transform = `translateY(calc(-50% + ${scrollPosition * 0.3}px))`;
    }
});

// ============================================
// MOUSE FOLLOW EFFECT ON CARDS
// ============================================
document.querySelectorAll('.pet-card').forEach(card => {
    card.addEventListener('mousemove', (e) => {
        const rect = card.getBoundingClientRect();
        const x = e.clientX - rect.left;
        const y = e.clientY - rect.top;
        
        const centerX = rect.width / 2;
        const centerY = rect.height / 2;
        
        const rotateX = (y - centerY) * 0.1;
        const rotateY = (centerX - x) * 0.1;
        
        card.style.transform = `perspective(1000px) rotateX(${rotateX}deg) rotateY(${rotateY}deg) translateZ(10px)`;
    });
    
    card.addEventListener('mouseleave', () => {
        card.style.transform = '';
    });
});

// ============================================
// DYNAMIC GLOW EFFECT
// ============================================
document.querySelectorAll('.pet-card').forEach(card => {
    const petImageWrapper = card.querySelector('.pet-image-wrapper');
    
    if (petImageWrapper) {
        card.addEventListener('mousemove', (e) => {
            const glow = document.createElement('div');
            glow.style.cssText = `
                position: absolute;
                width: 100px;
                height: 100px;
                background: radial-gradient(circle, rgba(255, 107, 107, 0.3) 0%, transparent 70%);
                border-radius: 50%;
                pointer-events: none;
                left: ${e.clientX - card.getBoundingClientRect().left - 50}px;
                top: ${e.clientY - card.getBoundingClientRect().top - 50}px;
                animation: fadeOut 0.8s ease-out;
            `;
            
            card.appendChild(glow);
            setTimeout(() => glow.remove(), 800);
        });
    }
});

// ============================================
// ADD FADE OUT ANIMATION
// ============================================
const style = document.createElement('style');
style.textContent = `
    @keyframes fadeOut {
        from {
            opacity: 1;
        }
        to {
            opacity: 0;
        }
    }
    
    @keyframes slideInUp {
        from {
            opacity: 0;
            transform: translateY(50px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }
`;
document.head.appendChild(style);

// ============================================
// PAGE LOAD ANIMATION
// ============================================
window.addEventListener('load', () => {
    document.body.style.animation = 'fadeIn 0.6s ease-out';
    
    // Add fade-in animation to head
    const style = document.createElement('style');
    style.textContent = `
        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }
    `;
    document.head.appendChild(style);
});

// ============================================
// NAVBAR SCROLL EFFECT
// ============================================
let lastScroll = 0;
const navbar = document.querySelector('.navbar');

window.addEventListener('scroll', () => {
    const currentScroll = window.pageYOffset;
    
    // Add shadow on scroll
    if (currentScroll > 50) {
        navbar.style.boxShadow = '0 10px 30px rgba(0, 0, 0, 0.2)';
    } else {
        navbar.style.boxShadow = 'var(--shadow)';
    }
    
    lastScroll = currentScroll;
});

// ============================================
// ACTIVE NAV LINK ON SCROLL
// ============================================
const sections = document.querySelectorAll('section[id]');
const navLinks = document.querySelectorAll('.nav-link');

window.addEventListener('scroll', () => {
    let current = '';
    
    sections.forEach(section => {
        const sectionTop = section.offsetTop;
        const sectionHeight = section.clientHeight;
        
        if (window.pageYOffset >= sectionTop - 200) {
            current = section.getAttribute('id');
        }
    });
    
    navLinks.forEach(link => {
        link.style.color = 'white';
        if (link.getAttribute('href').slice(1) === current) {
            link.style.color = 'var(--accent)';
        }
    });
});

// ============================================
// RANDOM PET COLOR PULSE
// ============================================
function randomPetAnimation() {
    const petCards = document.querySelectorAll('.pet-card');
    const randomCard = petCards[Math.floor(Math.random() * petCards.length)];
    
    if (randomCard) {
        const badge = randomCard.querySelector('.pet-badge');
        if (badge) {
            badge.style.animation = 'glow 0.6s ease-out';
            setTimeout(() => {
                badge.style.animation = '';
            }, 600);
        }
    }
}

// Call random animation every 3 seconds
setInterval(randomPetAnimation, 5000);

// ============================================
// TOUCH FRIENDLY - Mobile optimizations
// ============================================
if (window.matchMedia('(max-width: 768px)').matches) {
    petCards.forEach(card => {
        card.addEventListener('touchstart', () => {
            card.style.transform = 'translateY(-10px)';
        });
        
        card.addEventListener('touchend', () => {
            card.style.transform = '';
        });
    });
}

// ============================================
// DYNAMIC BACKGROUND COLOR SHIFT
// ============================================
const observerForBackground = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            if (entry.target.id === 'about') {
                entry.target.style.background = 'linear-gradient(135deg, #F7F9FC 0%, #E8F4F8 100%)';
            }
        }
    });
});

document.querySelectorAll('section').forEach(section => {
    observerForBackground.observe(section);
});

// ============================================
// CONSOLE WELCOME MESSAGE
// ============================================
console.log('%cWelcome to PawsAdopt! 🐾', 'font-size: 20px; color: #FF6B6B; font-weight: bold;');
console.log('%cFind your perfect pet today!', 'font-size: 14px; color: #4ECDC4;');
